package cv.pn.processmanagement.business.ocorrencia.services;

import cv.pn.processmanagement.business.CargaSigoAntigo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Cache em memória da listagem nacional do SIGO antigo.
 *
 * A base do SIGO antigo não tem índices nas colunas usadas pela listagem (data de registo, número,
 * assinaturas, processos), e cada pesquisa obrigava a ler a tabela inteira. Como o SIGO antigo já não
 * recebe ocorrências novas, o resumo de todas as ocorrências é lido uma vez ao arrancar e atualizado
 * de hora a hora; a pesquisa, os filtros, a contagem e a paginação são feitos em memória.
 *
 * Os valores agregados (naturezas, tipologias) são calculados pelo PostgreSQL na carga, com as mesmas
 * expressões da query SQL da listagem, para manter exatamente os mesmos textos e a mesma ordenação.
 */
@Component
public class OcorrenciaNacionalCache {

    private static final Logger log = LoggerFactory.getLogger(OcorrenciaNacionalCache.class);

    private static final long INTERVALO_ATUALIZACAO_MINUTOS = 60;

    private static final Comparator<Entrada> ORDEM_LISTAGEM = Comparator
            .comparing((Entrada e) -> e.dataLocal, Comparator.reverseOrder())
            .thenComparing(e -> e.oracleId, Comparator.reverseOrder());

    private static final List<DateTimeFormatter> FORMATOS_FILTRO_DATA_HORA = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSS][.SS][.S]"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private static final DateTimeFormatter FORMATO_FILTRO_DATA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String SQL_OCORRENCIAS = """
            SELECT o.oracle_id, o.num_ocorrencia, o.tp_auto, o.data_registo, o.num_processo_sij,
                   o.descricao_etapa, o.id_agente, o.id_comando,
                   e.oracle_id AS unidade_id, COALESCE(e.nome, '') AS unidade, e.sigla AS unidade_sigla,
                   cmd.oracle_id AS comando_id, COALESCE(cmd.nome, '') AS comando, cmd.sigla AS comando_sigla
            FROM sgo_ocorrencia o
            LEFT JOIN esquadra e ON e.oracle_id = o.id_esquadra
            LEFT JOIN esquadra cmd ON cmd.oracle_id = o.id_comando
            """;

    private static final String SQL_TIPOS = """
            SELECT x.id_ocorrencia,
                   string_agg(DISTINCT COALESCE(tp_pai.nome, t.nome), ', ' ORDER BY COALESCE(tp_pai.nome, t.nome)) AS naturezas,
                   string_agg(DISTINCT t.nome, ', ' ORDER BY t.nome) AS tipologias,
                   (array_agg(t.naturesa_crime ORDER BY x.ctid) FILTER (WHERE t.naturesa_crime IS NOT NULL AND t.naturesa_crime <> ''))[1] AS naturesa_crime,
                   array_agg(DISTINCT COALESCE(tp_pai.nome, t.nome)) AS lista_naturezas,
                   array_agg(DISTINCT t.nome) AS lista_tipologias,
                   array_agg(DISTINCT t.enquadramento) AS lista_enquadramentos
            FROM sgo_ocorrencia_tp_ocorrencia x
            JOIN sgo_tp_ocorrencia t ON t.oracle_id = x.id_tp_ocorrencia
            LEFT JOIN sgo_tp_ocorrencia tp_pai ON tp_pai.oracle_id = t.id_pai
            GROUP BY x.id_ocorrencia
            """;

    private static final String SQL_AUTUANTES = """
            SELECT DISTINCT ON (a.id_agente) a.id_agente, a.nome
            FROM assinatura a
            WHERE a.tipo_assinante = 'AUTUANTE' AND a.id_agente IS NOT NULL
            ORDER BY a.id_agente, a.ctid
            """;

    private static final String SQL_PROCESSOS = """
            SELECT pr.id_ocorrencia, pr.num_ocorrencia, pr.prazo, pr.data_registo
            FROM processo pr
            WHERE pr.prazo IS NOT NULL AND pr.prazo <> ''
            """;

    // Nome e alcunha de cada interveniente (pessoa) das ocorrências, para a pesquisa "Nome / Alcunha do Interveniente"
    private static final String SQL_INTERVENIENTES = """
            SELECT op.oracle_id_ocorrencia, p.nome, p.alcunhas
            FROM sgo_ocorrencia_pessoa op
            JOIN sigo_pessoa p ON p.oracle_id = op.oracle_id_pessoa
            """;
    // Separa os nomes guardados de cada ocorrência; não aparece no texto pesquisado (ver normalizarInterveniente)
    private static final char SEPARADOR_NOMES = '\n';

    private final JdbcTemplate jdbcTemplate;
    private final ScheduledExecutorService agendador = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "cache-ocorrencia-nacional");
        t.setDaemon(true);
        return t;
    });

    private volatile List<Entrada> entradas;
    // Número da ocorrência -> oracle_id (com índice), usado para acelerar o detalhe da ocorrência
    private volatile Map<String, Long> oracleIdPorNumero;
    // Nomes de todas as tipologias usadas nas ocorrências (para a pesquisa por tipologia semelhante)
    private volatile Set<String> tipologiasConhecidas;
    private volatile Date carregadoEm;
    private volatile long duracaoUltimaCargaMs;
    private volatile String ultimoErro;
    private volatile boolean aCarregar;

    public OcorrenciaNacionalCache(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void iniciar() {
        agendador.scheduleWithFixedDelay(this::carregarSemFalhar, 0, INTERVALO_ATUALIZACAO_MINUTOS, TimeUnit.MINUTES);
    }

    @PreDestroy
    public void parar() {
        agendador.shutdownNow();
    }

    public boolean isPronta() {
        return entradas != null;
    }

    public Map<String, Object> estado() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("pronta", isPronta());
        m.put("aCarregar", aCarregar);
        m.put("totalOcorrencias", entradas == null ? 0 : entradas.size());
        m.put("carregadoEm", carregadoEm);
        m.put("duracaoUltimaCargaMs", duracaoUltimaCargaMs);
        m.put("atualizacaoMinutos", INTERVALO_ATUALIZACAO_MINUTOS);
        m.put("ultimoErro", ultimoErro);
        return m;
    }

    private void carregarSemFalhar() {
        // Uma carga pesada de cada vez (ver CargaSigoAntigo)
        CargaSigoAntigo.UMA_DE_CADA_VEZ.lock();
        try {
            carregarAgora();
        } finally {
            CargaSigoAntigo.UMA_DE_CADA_VEZ.unlock();
        }
    }

    private void carregarAgora() {
        aCarregar = true;
        long inicio = System.currentTimeMillis();
        try {
            List<Entrada> novas = carregar();
            entradas = novas;
            oracleIdPorNumero = indicePorNumero(novas);
            tipologiasConhecidas = tipologiasUsadas(novas);
            carregadoEm = new Date();
            duracaoUltimaCargaMs = System.currentTimeMillis() - inicio;
            ultimoErro = null;
            log.info("Cache da ocorrência nacional (SIGO antigo) carregada: {} ocorrências em {} ms", novas.size(), duracaoUltimaCargaMs);
        } catch (Exception e) {
            ultimoErro = e.getMessage();
            log.error("Falha ao carregar a cache da ocorrência nacional; a listagem continua a usar a base de dados e há nova tentativa dentro de 1 minuto", e);
            // A ligação à base de teste cai de vez em quando: não esperar pela atualização de hora a hora
            agendador.schedule(this::carregarSemFalhar, 1, TimeUnit.MINUTES);
        } finally {
            aCarregar = false;
        }
    }

    private List<Entrada> carregar() {
        Map<Long, Tipos> tipos = new HashMap<>();
        CargaSigoAntigo.consultarEmBlocos(jdbcTemplate, SQL_TIPOS, rs -> {
            Tipos t = new Tipos();
            t.naturezas = rs.getString("naturezas");
            t.tipologias = rs.getString("tipologias");
            t.naturesaCrime = rs.getString("naturesa_crime");
            t.listaNaturezas = conjunto(rs.getArray("lista_naturezas"));
            t.listaTipologias = conjunto(rs.getArray("lista_tipologias"));
            t.listaEnquadramentos = conjunto(rs.getArray("lista_enquadramentos"));
            tipos.put(rs.getLong("id_ocorrencia"), t);
        });

        Map<Long, String> autuantes = new HashMap<>();
        CargaSigoAntigo.consultarEmBlocos(jdbcTemplate, SQL_AUTUANTES, rs -> {
            autuantes.put(rs.getLong("id_agente"), rs.getString("nome"));
        });

        Map<Long, List<Processo>> processosPorId = new HashMap<>();
        Map<String, List<Processo>> processosPorNumero = new HashMap<>();
        CargaSigoAntigo.consultarEmBlocos(jdbcTemplate, SQL_PROCESSOS, rs -> {
            Processo p = new Processo();
            p.prazo = rs.getString("prazo");
            p.dataRegisto = rs.getTimestamp("data_registo", utc());
            Long id = longOuNulo(rs, "id_ocorrencia");
            String num = rs.getString("num_ocorrencia");
            if (id != null) {
                processosPorId.computeIfAbsent(id, k -> new ArrayList<>()).add(p);
            }
            if (num != null) {
                processosPorNumero.computeIfAbsent(num, k -> new ArrayList<>()).add(p);
            }
        });

        Map<Long, StringBuilder> intervenientes = new HashMap<>();
        CargaSigoAntigo.consultarEmBlocos(jdbcTemplate, SQL_INTERVENIENTES, rs -> {
            StringBuilder nomes = intervenientes.computeIfAbsent(rs.getLong("oracle_id_ocorrencia"), k -> new StringBuilder());
            for (String nome : new String[]{rs.getString("nome"), rs.getString("alcunhas")}) {
                String n = normalizarInterveniente(nome);
                if (!n.isEmpty()) {
                    nomes.append(n).append(SEPARADOR_NOMES);
                }
            }
        });

        List<Entrada> lista = new ArrayList<>(110_000);
        CargaSigoAntigo.consultarEmBlocos(jdbcTemplate, SQL_OCORRENCIAS, rs -> {
            Entrada e = criarEntrada(rs, tipos, autuantes, processosPorId, processosPorNumero);
            StringBuilder nomes = intervenientes.get(e.oracleId);
            e.intervenientes = nomes == null ? "" : nomes.toString();
            lista.add(e);
        });

        lista.sort(ORDEM_LISTAGEM);
        return Collections.unmodifiableList(lista);
    }

    private Entrada criarEntrada(ResultSet rs, Map<Long, Tipos> tipos, Map<Long, String> autuantes,
                                 Map<Long, List<Processo>> processosPorId,
                                 Map<String, List<Processo>> processosPorNumero) throws SQLException {
        Entrada e = new Entrada();
        e.oracleId = rs.getLong("oracle_id");
        e.numOcorrencia = rs.getString("num_ocorrencia");
        e.numMinusculas = e.numOcorrencia == null ? "" : e.numOcorrencia.toLowerCase(Locale.ROOT);
        e.tpAuto = rs.getString("tp_auto");
        Timestamp data = rs.getTimestamp("data_registo", utc());
        e.dataRegisto = data == null ? null : new Date(data.getTime());
        e.dataLocal = data == null ? LocalDateTime.MIN : data.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
        e.idComando = longOuNulo(rs, "id_comando");
        e.unidadeId = longOuNulo(rs, "unidade_id");
        e.unidadeSigla = rs.getString("unidade_sigla");
        e.comandoId = longOuNulo(rs, "comando_id");
        e.comandoSigla = rs.getString("comando_sigla");

        String etapa = rs.getString("descricao_etapa");
        String etapaNormalizada = etapa == null ? "" : etapa.trim().toUpperCase(Locale.ROOT);
        e.estadoCodigo = codigoEstado(etapaNormalizada);

        Tipos t = tipos.get(e.oracleId);
        if (t != null) {
            e.naturezasFiltro = t.listaNaturezas;
            e.tipologiasFiltro = t.listaTipologias;
            e.enquadramentosFiltro = t.listaEnquadramentos;
        }

        Long idAgente = longOuNulo(rs, "id_agente");

        OcorrenciaNacionalDto dto = new OcorrenciaNacionalDto();
        dto.oracleIdOcorrencia = e.oracleId;
        dto.numOcorrencia = e.numOcorrencia;
        dto.dataRegisto = e.dataRegisto;
        dto.tpAuto = e.tpAuto;
        dto.autoPrincipal = autoPrincipal(e.tpAuto);
        dto.numProcesso = rs.getString("num_processo_sij");
        dto.unidade = rs.getString("unidade");
        dto.comando = rs.getString("comando");
        dto.fase = etapa == null ? "AGUARDA ANÁLISE" : etapa;
        dto.estado = nomeEstado(e.estadoCodigo);
        dto.estadoCodigo = e.estadoCodigo;
        dto.registradoPor = idAgente == null ? "" : autuantes.getOrDefault(idAgente, "");
        dto.prazo = prazo(e.oracleId, e.numOcorrencia, processosPorId, processosPorNumero);
        dto.tipo = t == null ? "Criminal" : tipo(t.naturesaCrime);
        dto.naturezas = t == null || t.naturezas == null ? "" : t.naturezas;
        dto.tipologias = t == null || t.tipologias == null ? "" : t.tipologias;
        e.dto = dto;
        return e;
    }

    /**
     * Pesquisa em memória com a mesma semântica da query SQL da listagem.
     * Devolve vazio se a cache ainda não estiver pronta ou se uma data do filtro não for reconhecida
     * (nesse caso a listagem usa a base de dados, como antes).
     */
    public Optional<Resultado> pesquisar(String numFiltro, String tpAuto, String estados, String dataInicio, String dataFim,
                                         String natureza, String tipologia, Set<String> tipologiasAceites,
                                         String enquadramento, Long comando, Long unidade, String comandoSigla, String unidadeSigla,
                                         String interveniente, int offset, int tamanho) {
        List<Entrada> todas = entradas;
        if (todas == null) {
            return Optional.empty();
        }

        LocalDateTime inicio = null;
        LocalDateTime fim = null;
        if (dataInicio != null) {
            inicio = lerData(dataInicio);
            if (inicio == null) {
                return Optional.empty();
            }
        }
        if (dataFim != null) {
            fim = lerData(dataFim);
            if (fim == null) {
                return Optional.empty();
            }
        }

        String numero = numFiltro == null ? null : numFiltro.toLowerCase(Locale.ROOT);
        String nomeInterveniente = interveniente == null ? null : normalizarInterveniente(interveniente);
        if (nomeInterveniente != null && nomeInterveniente.isEmpty()) {
            nomeInterveniente = null;
        }
        Set<String> codigosEstado = estados == null ? null : new HashSet<>(Arrays.asList(estados.split(",")));

        List<OcorrenciaNacionalDto> encontradas = new ArrayList<>();
        int total = 0;
        for (Entrada e : todas) {
            if (numero != null && !e.numMinusculas.contains(numero)) continue;
            if (tpAuto != null && !tpAuto.equals(e.tpAuto)) continue;
            if (codigosEstado != null && !codigosEstado.contains(e.estadoCodigo)) continue;
            if (inicio != null && e.dataLocal.isBefore(inicio)) continue;
            if (fim != null && e.dataLocal.isAfter(fim)) continue;
            if (natureza != null && !e.naturezasFiltro.contains(natureza)) continue;
            if (tipologia != null && !e.tipologiasFiltro.contains(tipologia)) continue;
            if (tipologiasAceites != null && Collections.disjoint(tipologiasAceites, e.tipologiasFiltro)) continue;
            if (enquadramento != null && !e.enquadramentosFiltro.contains(enquadramento)) continue;
            if (comando != null && !comando.equals(e.comandoId)) continue;
            if (unidade != null && !unidade.equals(e.unidadeId) && !unidade.equals(e.idComando)) continue;
            if (comandoSigla != null && !comandoSigla.equals(e.comandoSigla)) continue;
            if (unidadeSigla != null && !unidadeSigla.equals(e.unidadeSigla)) continue;
            if (nomeInterveniente != null && !e.intervenientes.contains(nomeInterveniente)) continue;

            if (total >= offset && encontradas.size() < tamanho) {
                encontradas.add(e.dto);
            }
            total++;
        }

        return Optional.of(new Resultado(encontradas, total));
    }

    /** Nomes das tipologias usadas nas ocorrências (vazio se a cache ainda não estiver pronta). */
    public Optional<Set<String>> tipologiasConhecidas() {
        return Optional.ofNullable(tipologiasConhecidas);
    }

    private static Set<String> tipologiasUsadas(List<Entrada> lista) {
        Set<String> nomes = new HashSet<>();
        for (Entrada e : lista) {
            nomes.addAll(e.tipologiasFiltro);
        }
        nomes.remove(null);
        return Collections.unmodifiableSet(nomes);
    }

    /** oracle_id da ocorrência com este número (vazio se a cache não estiver pronta ou o número for repetido). */
    public Optional<Long> oracleIdPorNumero(String numOcorrencia) {
        Map<String, Long> indice = oracleIdPorNumero;
        if (indice == null || numOcorrencia == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(indice.get(numOcorrencia.trim()));
    }

    private static Map<String, Long> indicePorNumero(List<Entrada> lista) {
        Map<String, Long> indice = new HashMap<>();
        Set<String> repetidos = new HashSet<>();
        for (Entrada e : lista) {
            if (e.numOcorrencia == null) continue;
            Long anterior = indice.put(e.numOcorrencia, e.oracleId);
            if (anterior != null && anterior != e.oracleId) {
                repetidos.add(e.numOcorrencia);
            }
        }
        repetidos.forEach(indice::remove);
        return indice;
    }

    private LocalDateTime lerData(String valor) {
        String v = valor.trim().replace('T', ' ');
        for (DateTimeFormatter f : FORMATOS_FILTRO_DATA_HORA) {
            try {
                return LocalDateTime.parse(v, f);
            } catch (Exception ignored) {
                // tenta o formato seguinte
            }
        }
        try {
            return java.time.LocalDate.parse(v, FORMATO_FILTRO_DATA).atStartOfDay();
        } catch (Exception ignored) {
            return null;
        }
    }

    // Mesmas regras da query SQL (CASE sobre descricao_etapa)
    private static String codigoEstado(String etapaNormalizada) {
        switch (etapaNormalizada) {
            case "ARQUIVADO":
                return "R";
            case "ENVIADO AO MP":
            case "ENVIADO AO CRSSM":
            case "ENVIADO AO EICC":
                return "F";
            case "AGUARDA ANÁLISE":
            case "PENDENTE ANÁLISE":
                return "E";
            default:
                return "A";
        }
    }

    private static String nomeEstado(String codigo) {
        switch (codigo) {
            case "R":
                return "Arquivado";
            case "F":
                return "Fechado";
            case "E":
                return "Em análise";
            default:
                return "Aberto";
        }
    }

    private static String autoPrincipal(String tpAuto) {
        if (tpAuto == null) {
            return "Outro";
        }
        switch (tpAuto) {
            case "1":
                return "Auto de Denúncia";
            case "2":
                return "Auto de Notícia";
            case "3":
                return "Auto de Detenção";
            case "4":
                return "Auto de Denúncia VBG";
            case "5":
                return "Auto de Identificação";
            default:
                return "Outro";
        }
    }

    private static String tipo(String naturesaCrime) {
        if (naturesaCrime == null) {
            return "Criminal";
        }
        switch (naturesaCrime) {
            case "PUBLICO":
                return "Público";
            case "SEMIPUBLICO":
                return "Semi-público";
            case "PARTICULAR":
                return "Particular";
            default:
                return "Criminal";
        }
    }

    /** Prazo do processo mais recente ligado à ocorrência (por id ou por número), como na query SQL. */
    private static String prazo(Long oracleId, String numOcorrencia,
                                Map<Long, List<Processo>> porId, Map<String, List<Processo>> porNumero) {
        Processo escolhido = null;
        for (List<Processo> candidatos : Arrays.asList(porId.get(oracleId), numOcorrencia == null ? null : porNumero.get(numOcorrencia))) {
            if (candidatos == null) continue;
            for (Processo p : candidatos) {
                if (escolhido == null || maisRecente(p, escolhido)) {
                    escolhido = p;
                }
            }
        }
        return escolhido == null ? "" : escolhido.prazo;
    }

    // ORDER BY data_registo DESC: no PostgreSQL os nulos vêm primeiro
    private static boolean maisRecente(Processo a, Processo b) {
        if (a.dataRegisto == null) return b.dataRegisto != null;
        if (b.dataRegisto == null) return false;
        return a.dataRegisto.after(b.dataRegisto);
    }

    private static Set<String> conjunto(Array array) throws SQLException {
        if (array == null) {
            return Collections.emptySet();
        }
        Object[] valores = (Object[]) array.getArray();
        return Arrays.stream(valores).filter(v -> v != null).map(Object::toString).collect(Collectors.toUnmodifiableSet());
    }

    private static Long longOuNulo(ResultSet rs, String coluna) throws SQLException {
        long v = rs.getLong(coluna);
        return rs.wasNull() ? null : v;
    }

    // hibernate.jdbc.time_zone=UTC: as datas são lidas como UTC para darem o mesmo resultado da listagem via Hibernate
    private static Calendar utc() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    public static final class Resultado {
        private final List<OcorrenciaNacionalDto> registos;
        private final long total;

        Resultado(List<OcorrenciaNacionalDto> registos, long total) {
            this.registos = registos;
            this.total = total;
        }

        public List<OcorrenciaNacionalDto> getRegistos() {
            return registos;
        }

        public long getTotal() {
            return total;
        }
    }

    private static final class Entrada {
        long oracleId;
        String numOcorrencia;
        String numMinusculas;
        String tpAuto;
        Date dataRegisto;
        LocalDateTime dataLocal;
        String estadoCodigo;
        Long idComando;
        Long unidadeId;
        String unidadeSigla;
        Long comandoId;
        String comandoSigla;
        Set<String> naturezasFiltro = Collections.emptySet();
        Set<String> tipologiasFiltro = Collections.emptySet();
        Set<String> enquadramentosFiltro = Collections.emptySet();
        // Nomes e alcunhas dos intervenientes, normalizados e separados por SEPARADOR_NOMES
        String intervenientes = "";
        OcorrenciaNacionalDto dto;
    }

    /**
     * Igual ao SIGOIC (APIUtilities.replaceSpecialCharacters + lower/unaccent na query): sem acentos nem outros
     * caracteres fora do ASCII e em minúsculas. A pesquisa é "contém", no nome ou na alcunha.
     */
    public static String normalizarInterveniente(String texto) {
        if (texto == null) return "";
        String semAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        return semAcentos.replace(SEPARADOR_NOMES, ' ').toLowerCase(Locale.ROOT);
    }

    private static final class Tipos {
        String naturezas;
        String tipologias;
        String naturesaCrime;
        Set<String> listaNaturezas;
        Set<String> listaTipologias;
        Set<String> listaEnquadramentos;
    }

    private static final class Processo {
        String prazo;
        Timestamp dataRegisto;
    }
}
