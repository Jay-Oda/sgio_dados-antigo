package cv.pn.processmanagement.business.ocorrencia.services;

import cv.pn.processmanagement.business.ocorrencia.repository.SgoOcorrenciaRepository;
import cv.pn.processmanagement.business.projection.OcorrenciaHistoricoProjection;
import cv.pn.processmanagement.business.projection.OcorrenciaNacionalProjection;
import cv.pn.processmanagement.business.projection.OpcaoProjection;
import cv.pn.processmanagement.utilities.APIResponse;
import cv.pn.processmanagement.utilities.Pagination;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class OcorrenciaNacionalService implements IOcorrenciaNacionalService {

    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter FORMATO_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // Formatos de data encontrados nas colunas de texto do SIGO antigo (ex.: "09-06-2025", "25.08.28")
    private static final List<DateTimeFormatter> FORMATOS_DATA_HORA_ANTIGOS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSSSSS][.SSS][.S]"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    private static final List<DateTimeFormatter> FORMATOS_DATA_ANTIGOS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("yy.MM.dd"));

    private static final Set<String> ESTADOS_VALIDOS = Set.of("A", "F", "E", "R");

    // O SIGO antigo já não recebe ocorrências novas e a contagem é cara (a tabela não tem índices),
    // por isso o total de cada combinação de filtros fica guardado durante alguns minutos.
    private static final long VALIDADE_TOTAL_MS = 10 * 60 * 1000L;
    private final Map<String, long[]> cacheTotais = new ConcurrentHashMap<>();

    private final SgoOcorrenciaRepository sgoOcorrenciaRepository;
    private final OcorrenciaNacionalCache ocorrenciaNacionalCache;

    public OcorrenciaNacionalService(SgoOcorrenciaRepository sgoOcorrenciaRepository,
                                     OcorrenciaNacionalCache ocorrenciaNacionalCache) {
        this.sgoOcorrenciaRepository = sgoOcorrenciaRepository;
        this.ocorrenciaNacionalCache = ocorrenciaNacionalCache;
    }

    @Override
    public Map<String, Object> estadoCache() {
        return ocorrenciaNacionalCache.estado();
    }

    @Override
    public APIResponse listarOcorrencias(int page,
                                         int size,
                                         String numOcorrencia,
                                         String tpAuto,
                                         String estado,
                                         String dataInicio,
                                         String dataFim,
                                         String natureza,
                                         String tipologia,
                                         String tipologiaSemelhante,
                                         String enquadramento,
                                         Long comando,
                                         Long unidade,
                                         String comandoSigla,
                                         String unidadeSigla,
                                         String interveniente,
                                         Integer offset) {

        try {

            int pagina = Math.max(page, 0);
            int tamanho = Math.min(Math.max(size, 1), offset != null ? 150 : 100);
            int deslocamento = offset != null ? Math.max(offset, 0) : pagina * tamanho;
            if (offset != null) {
                pagina = deslocamento / tamanho;
            }

            String numFiltro = (numOcorrencia != null && !numOcorrencia.trim().isEmpty())
                    ? "%" + numOcorrencia.trim() + "%" : null;

            String estadoFiltro = normalizarEstados(estado);
            String dataInicioSql = normalizarDataHora(dataInicio);
            String dataFimSql = normalizarDataHora(dataFim);
            String comandoSiglaFiltro = textoOuNulo(comandoSigla);
            String unidadeSiglaFiltro = textoOuNulo(unidadeSigla);

            // Tipologia escolhida no SIGOIC: tipologias do SIGO antigo com nome semelhante (80%, sem acentos
            // nem maiúsculas). Se nenhuma for semelhante, não há ocorrências do SIGO antigo para mostrar.
            Set<String> tipologiasAceites = null;
            String nomeTipologia = textoOuNulo(tipologiaSemelhante);
            if (nomeTipologia != null) {
                Collection<String> conhecidas = ocorrenciaNacionalCache.tipologiasConhecidas().isPresent()
                        ? ocorrenciaNacionalCache.tipologiasConhecidas().get()
                        : sgoOcorrenciaRepository.buscarTipologias();
                tipologiasAceites = conhecidas.stream()
                        .filter(nome -> SemelhancaTexto.semelhantes(nomeTipologia, nome))
                        .collect(Collectors.toSet());
                if (tipologiasAceites.isEmpty()) {
                    return semResultados(pagina, tamanho);
                }
            }

            // Com a cache pronta, a pesquisa é feita em memória (milissegundos); senão usa a base como antes
            Optional<OcorrenciaNacionalCache.Resultado> daCache = ocorrenciaNacionalCache.pesquisar(
                    numFiltro == null ? null : numOcorrencia.trim(), tpAuto, estadoFiltro, dataInicioSql, dataFimSql,
                    natureza, tipologia, tipologiasAceites, enquadramento, comando, unidade,
                    comandoSiglaFiltro, unidadeSiglaFiltro, textoOuNulo(interveniente), deslocamento, tamanho);
            if (daCache.isPresent()) {
                long totalCache = daCache.get().getTotal();
                return new APIResponse.buildAPIResponse()
                        .setStatus(true)
                        .setStatusText("Ocorrências encontradas com sucesso")
                        .setDetails(new ArrayList<>(daCache.get().getRegistos()))
                        .setPagination(new Pagination(pagina, tamanho, (int) Math.ceil((double) totalCache / tamanho), totalCache))
                        .builder();
            }

            // A pesquisa por interveniente só existe na cache (a query SQL não lê as pessoas)
            if (textoOuNulo(interveniente) != null) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Os dados do SIGO antigo ainda estão a carregar")
                        .setDetails(Collections.emptyList())
                        .builder();
            }

            // Sem a cache, a query SQL só filtra por um nome de tipologia
            if (tipologiasAceites != null) {
                String unica = tipologiasAceites.iterator().next();
                if (tipologiasAceites.size() > 1) {
                    return new APIResponse.buildAPIResponse()
                            .setStatus(false)
                            .setStatusText("Os dados do SIGO antigo ainda estão a carregar")
                            .setDetails(Collections.emptyList())
                            .builder();
                }
                if (tipologia != null && !tipologia.equals(unica)) {
                    return semResultados(pagina, tamanho);
                }
                tipologia = unica;
            }

            List<OcorrenciaNacionalProjection> registos = sgoOcorrenciaRepository.buscarOcorrenciasNacionais(
                    numFiltro, tpAuto, estadoFiltro, dataInicioSql, dataFimSql,
                    natureza, tipologia, enquadramento, comando, unidade,
                    comandoSiglaFiltro, unidadeSiglaFiltro,
                    deslocamento, tamanho);

            String chaveTotal = String.join("|",
                    String.valueOf(numFiltro), String.valueOf(tpAuto), String.valueOf(estadoFiltro),
                    String.valueOf(dataInicioSql), String.valueOf(dataFimSql), String.valueOf(natureza),
                    String.valueOf(tipologia), String.valueOf(enquadramento), String.valueOf(comando),
                    String.valueOf(unidade), String.valueOf(comandoSiglaFiltro), String.valueOf(unidadeSiglaFiltro));

            long agora = System.currentTimeMillis();
            long[] emCache = cacheTotais.get(chaveTotal);
            long total;
            if (emCache != null && agora - emCache[1] < VALIDADE_TOTAL_MS) {
                total = emCache[0];
            } else {
                total = sgoOcorrenciaRepository.countOcorrenciasNacionais(
                        numFiltro, tpAuto, estadoFiltro, dataInicioSql, dataFimSql,
                        natureza, tipologia, enquadramento, comando, unidade,
                        comandoSiglaFiltro, unidadeSiglaFiltro);
                cacheTotais.put(chaveTotal, new long[]{total, agora});
            }

            int pageCount = (int) Math.ceil((double) total / tamanho);

            List<Object> details = new ArrayList<>(registos);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Ocorrências encontradas com sucesso")
                    .setDetails(details)
                    .setPagination(new Pagination(pagina, tamanho, pageCount, total))
                    .builder();

        } catch (Exception e) {
            e.printStackTrace();
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao listar ocorrências: " + e.getMessage())
                    .setDetails(Collections.emptyList())
                    .builder();
        }
    }

    @Override
    public Map<String, Object> obterOpcoes() {
        Map<String, Object> opcoes = new LinkedHashMap<>();

        opcoes.put("tiposAutos", List.of(
                Map.of("codigo", "1", "nome", "Auto de Denúncia"),
                Map.of("codigo", "2", "nome", "Auto de Notícia"),
                Map.of("codigo", "3", "nome", "Auto de Detenção"),
                Map.of("codigo", "4", "nome", "Auto de Denúncia VBG"),
                Map.of("codigo", "5", "nome", "Auto de Identificação")
        ));

        opcoes.put("estados", List.of(
                Map.of("codigo", "A", "nome", "Aberto"),
                Map.of("codigo", "F", "nome", "Fechado"),
                Map.of("codigo", "E", "nome", "Em análise"),
                Map.of("codigo", "R", "nome", "Arquivado")
        ));

        opcoes.put("naturezas", sgoOcorrenciaRepository.buscarNaturezas());
        opcoes.put("tipologias", sgoOcorrenciaRepository.buscarTipologias());
        opcoes.put("enquadramentos", sgoOcorrenciaRepository.buscarEnquadramentos());

        List<Map<String, Object>> comandos = new ArrayList<>();
        for (OpcaoProjection cmd : sgoOcorrenciaRepository.buscarComandos()) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("valor", cmd.getValor());
            m.put("nome", cmd.getNome());
            comandos.add(m);
        }
        opcoes.put("comandos", comandos);

        List<Map<String, Object>> unidades = new ArrayList<>();
        for (OpcaoProjection uni : sgoOcorrenciaRepository.buscarUnidades()) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("valor", uni.getValor());
            m.put("nome", uni.getNome());
            unidades.add(m);
        }
        opcoes.put("unidades", unidades);

        return opcoes;
    }

    /**
     * Histórico de uma ocorrência do SIGO antigo, montado a partir das colunas de cada etapa
     * de sgo_ocorrencia (o SIGO antigo não tem tabela de histórico). Cada entrada usa os mesmos
     * nomes de campos do histórico do SIGOIC: dateCreate, phaseDescription, statusDescription,
     * userCreate e decision.
     */
    @Override
    public APIResponse obterHistorico(String numOcorrencia) {
        try {
            if (numOcorrencia == null || numOcorrencia.trim().isEmpty()) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Número da ocorrência obrigatório")
                        .setDetails(Collections.emptyList())
                        .builder();
            }

            OcorrenciaHistoricoProjection o = sgoOcorrenciaRepository.buscarHistoricoOcorrencia(numOcorrencia.trim());
            if (o == null) {
                return new APIResponse.buildAPIResponse()
                        .setStatus(false)
                        .setStatusText("Ocorrência não encontrada")
                        .setDetails(Collections.emptyList())
                        .builder();
            }

            List<Object> historico = new ArrayList<>();

            // hibernate.jdbc.time_zone=UTC: o valor da base (ex.: 13:55) chega como instante UTC
            String dataRegisto = o.getDataRegisto() == null ? null
                    : o.getDataRegisto().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime().format(FORMATO_ISO);
            historico.add(entradaHistorico(dataRegisto, null, "Registo", "Registada",
                    o.getRegistradoPor(), null));

            if (temValor(o.getDataAnalise()) || temValor(o.getUserAnalise()) || temValor(o.getTipoAnalise())) {
                historico.add(entradaHistorico(converterData(o.getDataAnalise()), o.getDataAnalise(), "Análise",
                        o.getTipoAnalise(), o.getUserAnalise(), o.getObsAnalise()));
            }

            if (temValor(o.getDataDespacho()) || temValor(o.getUserDespacho()) || temValor(o.getTipoDespacho())) {
                historico.add(entradaHistorico(converterData(o.getDataDespacho()), o.getDataDespacho(), "Despacho",
                        o.getTipoDespacho(), o.getUserDespacho(), o.getObsDespacho()));
            }

            if (temValor(o.getDataEnviado()) || temValor(o.getUserEnviado())) {
                String etapa = o.getDescricaoEtapa() != null && o.getDescricaoEtapa().toUpperCase().startsWith("ENVIADO")
                        ? o.getDescricaoEtapa() : "Enviado";
                historico.add(entradaHistorico(converterData(o.getDataEnviado()), o.getDataEnviado(), "Envio",
                        etapa, o.getUserEnviado(), null));
            }

            if (temValor(o.getDataEnvioSij())) {
                historico.add(entradaHistorico(converterData(o.getDataEnvioSij()), o.getDataEnvioSij(), "Envio ao SIJ",
                        "Enviado ao SIJ", null, null));
            }

            if (temValor(o.getDataArquiva()) || temValor(o.getUserArquiva())) {
                historico.add(entradaHistorico(converterData(o.getDataArquiva()), o.getDataArquiva(), "Arquivo",
                        "Arquivado", o.getUserArquiva(), o.getMotivoArquiva()));
            }

            if (temValor(o.getDataFecho()) || temValor(o.getUserFecho()) || temValor(o.getTipoFecho())) {
                historico.add(entradaHistorico(converterData(o.getDataFecho()), o.getDataFecho(), "Fecho",
                        o.getTipoFecho(), o.getUserFecho(), null));
            }

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Histórico da ocorrência " + o.getNumOcorrencia())
                    .setDetails(historico)
                    .builder();

        } catch (Exception e) {
            e.printStackTrace();
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao obter histórico: " + e.getMessage())
                    .setDetails(Collections.emptyList())
                    .builder();
        }
    }

    private Map<String, Object> entradaHistorico(String dataIso, String dataOriginal, String fase,
                                                 String estado, String utilizador, String decisao) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("dateCreate", dataIso);
        m.put("dataOriginal", dataIso == null ? textoOuNulo(dataOriginal) : null);
        m.put("phaseDescription", fase);
        m.put("statusDescription", temValor(estado) ? estado.trim() : null);
        m.put("userCreate", temValor(utilizador) ? utilizador.trim() : null);
        m.put("decision", temValor(decisao) ? decisao.trim() : null);
        return m;
    }

    /** Converte as datas guardadas como texto no SIGO antigo para ISO (yyyy-MM-dd'T'HH:mm:ss); null se não for possível. */
    private String converterData(String valor) {
        if (!temValor(valor)) {
            return null;
        }
        String v = valor.trim();
        for (DateTimeFormatter f : FORMATOS_DATA_HORA_ANTIGOS) {
            try {
                return LocalDateTime.parse(v, f).format(FORMATO_ISO);
            } catch (Exception ignored) {
                // tenta o formato seguinte
            }
        }
        for (DateTimeFormatter f : FORMATOS_DATA_ANTIGOS) {
            try {
                return LocalDate.parse(v, f).atStartOfDay().format(FORMATO_ISO);
            } catch (Exception ignored) {
                // tenta o formato seguinte
            }
        }
        return null;
    }

    /** Aceita um código (A/F/E/R) ou vários separados por vírgula; ignora valores desconhecidos. */
    private String normalizarEstados(String estado) {
        if (!temValor(estado)) {
            return null;
        }
        List<String> codigos = Arrays.stream(estado.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(ESTADOS_VALIDOS::contains)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        if (codigos.isEmpty() || codigos.size() == ESTADOS_VALIDOS.size()) {
            return null;
        }
        return String.join(",", codigos);
    }

    private boolean temValor(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    private APIResponse semResultados(int pagina, int tamanho) {
        return new APIResponse.buildAPIResponse()
                .setStatus(true)
                .setStatusText("Ocorrências encontradas com sucesso")
                .setDetails(Collections.emptyList())
                .setPagination(new Pagination(pagina, tamanho, 0, 0L))
                .builder();
    }

    private String textoOuNulo(String valor) {
        return temValor(valor) ? valor.trim() : null;
    }

    private String normalizarDataHora(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        String v = valor.trim();
        try {
            LocalDateTime dt = LocalDateTime.parse(v.replace("T", " "), FORMATO_DATA_HORA);
            return dt.format(FORMATO_DATA_HORA);
        } catch (Exception e) {
            return v;
        }
    }
}
