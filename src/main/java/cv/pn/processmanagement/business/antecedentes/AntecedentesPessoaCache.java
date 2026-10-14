package cv.pn.processmanagement.business.antecedentes;

import cv.pn.processmanagement.business.CargaSigoAntigo;
import cv.pn.processmanagement.business.ocorrencia.services.SemelhancaTexto;
import cv.pn.processmanagement.utilities.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Antecedentes de pessoas do SIGO antigo, pesquisados por nome + nome do pai + nome da mãe.
 *
 * Os nomes vindos do SIGOIC/RNI e os do SIGO antigo nem sempre estão escritos da mesma forma
 * (maiúsculas, acentos, espaços repetidos e até a ordem dos apelidos: "Lúcio Alexandre Tavares Da Cruz
 * De Pina" no RNI e "LUCIO ALEXANDRE TAVARES  DE PINA DA CRUZ" no SIGO antigo). Por isso cada nome é
 * reduzido às suas palavras (sem acentos, em maiúsculas, sem partículas de/da/do/das/dos/e), e dois nomes
 * "coincidem" quando têm as mesmas palavras ou são pelo menos 80% iguais (pela ordem escrita ou pelas
 * palavras ordenadas), o que aceita erros de digitação ("Ivam"/"YVAM", "Sunday"/"Suwday").
 *
 * Regra definida pelo utilizador em 2026-10-14: é a mesma pessoa quando o nome coincide e
 * - no SIGO antigo não há pai nem mãe registados; ou
 * - só há um deles registado e esse coincide com o do RNI; ou
 * - há os dois registados e pelo menos um coincide.
 * A data de nascimento não conta.
 *
 * Contam como antecedentes as mesmas condições do endpoint busca/antecidente: Denunciado (Auto de
 * Denúncia), Detido (Auto de Detenção, condição 5) e Denunciado/Autor (Auto de Denúncia VBG).
 * Os dados são lidos uma vez ao arrancar e atualizados de hora a hora (o SIGO antigo já não recebe dados).
 */
@Component
public class AntecedentesPessoaCache {

    private static final Logger log = LoggerFactory.getLogger(AntecedentesPessoaCache.class);

    private static final long INTERVALO_ATUALIZACAO_MINUTOS = 60;
    private static final Set<String> PARTICULAS = Set.of("DE", "DA", "DO", "DAS", "DOS", "E", "D");
    private static final String SEM_ANTECEDENTES = "Antecedente(s) SIGO\nAviso!\nNão foi encontrado nenhum registo(s) de antecedente(s).";

    private static final String SQL_ANTECEDENTES = """
            SELECT p.oracle_id AS pessoa_id, p.nome, p.pai, p.mae,
                   o.num_ocorrencia, o.data_registo, o.tp_auto, op.condicao,
                   COALESCE(tp_pai.nome, tp.nome, '') AS natureza,
                   COALESCE(tp.nome, '') AS tipologia,
                   COALESCE(e.nome, '') AS unidade,
                   COALESCE(o.descricao_etapa, 'AGUARDA ANÁLISE') AS fase
            FROM sigo_pessoa p
            JOIN sgo_ocorrencia_pessoa op ON op.oracle_id_pessoa = p.oracle_id
            JOIN sgo_ocorrencia o ON o.oracle_id = op.oracle_id_ocorrencia
            LEFT JOIN sgo_ocorrencia_tp_ocorrencia otp ON otp.id_ocorrencia = o.oracle_id
            LEFT JOIN sgo_tp_ocorrencia tp ON tp.oracle_id = otp.id_tp_ocorrencia
            LEFT JOIN sgo_tp_ocorrencia tp_pai ON tp_pai.oracle_id = tp.id_pai
            LEFT JOIN esquadra e ON e.oracle_id = o.id_esquadra
            WHERE (o.tp_auto = '1' AND op.condicao = '3')
               OR (o.tp_auto = '3' AND op.condicao = '5')
               OR (o.tp_auto = '4' AND op.condicao = '3')
            """;

    private final JdbcTemplate jdbcTemplate;
    private final ScheduledExecutorService agendador = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "cache-antecedentes-pessoa");
        t.setDaemon(true);
        return t;
    });

    /** Chave = palavras do nome da pessoa; valor = pessoas do SIGO antigo com esse nome. */
    private volatile Map<String, List<Pessoa>> pessoasPorNome;
    /** Todas as pessoas, para comparar nomes com erros de digitação (pelo menos 80% iguais). */
    private volatile List<Pessoa> todasPessoas;
    private volatile int totalPessoas;
    private volatile java.util.Date carregadoEm;
    private volatile long duracaoUltimaCargaMs;
    private volatile String ultimoErro;

    public AntecedentesPessoaCache(JdbcTemplate jdbcTemplate) {
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

    public Map<String, Object> estado() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("pronta", pessoasPorNome != null);
        m.put("pessoasComAntecedentes", totalPessoas);
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
        long inicio = System.currentTimeMillis();
        try {
            Map<Long, Pessoa> porId = new HashMap<>();
            CargaSigoAntigo.consultarEmBlocos(jdbcTemplate, SQL_ANTECEDENTES, rs -> {
                long id = rs.getLong("pessoa_id");
                Pessoa p = porId.get(id);
                if (p == null) {
                    p = new Pessoa();
                    p.chaveNome = chave(rs.getString("nome"));
                    p.nomePorOrdem = nomePorOrdem(rs.getString("nome"));
                    p.chavePai = chave(rs.getString("pai"));
                    p.chaveMae = chave(rs.getString("mae"));
                    p.paiPorOrdem = nomePorOrdem(rs.getString("pai"));
                    p.maePorOrdem = nomePorOrdem(rs.getString("mae"));
                    porId.put(id, p);
                }
                Linha l = new Linha();
                l.numOcorrencia = rs.getString("num_ocorrencia");
                Timestamp data = rs.getTimestamp("data_registo", Calendar.getInstance(TimeZone.getTimeZone("UTC")));
                l.dataRegisto = data == null ? null : data.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
                l.tpAuto = rs.getString("tp_auto");
                l.natureza = rs.getString("natureza");
                l.tipologia = rs.getString("tipologia");
                l.unidade = rs.getString("unidade");
                l.fase = rs.getString("fase");
                p.linhas.add(l);
            });

            Map<String, List<Pessoa>> indice = new HashMap<>();
            List<Pessoa> todas = new ArrayList<>();
            for (Pessoa p : porId.values()) {
                if (!p.chaveNome.isEmpty()) {
                    indice.computeIfAbsent(p.chaveNome, k -> new ArrayList<>()).add(p);
                    todas.add(p);
                }
            }

            todasPessoas = todas;
            pessoasPorNome = indice;
            totalPessoas = porId.size();
            carregadoEm = new java.util.Date();
            duracaoUltimaCargaMs = System.currentTimeMillis() - inicio;
            ultimoErro = null;
            log.info("Cache de antecedentes (SIGO antigo) carregada: {} pessoas em {} ms", totalPessoas, duracaoUltimaCargaMs);
        } catch (Exception e) {
            ultimoErro = e.getMessage();
            log.error("Falha ao carregar a cache de antecedentes do SIGO antigo; nova tentativa dentro de 1 minuto", e);
            // A ligação à base de teste cai de vez em quando: não esperar pela atualização de hora a hora
            agendador.schedule(this::carregarSemFalhar, 1, TimeUnit.MINUTES);
        }
    }

    /** A data de nascimento continua a ser recebida (a interface envia-a), mas não conta desde 2026-10-14. */
    public APIResponse pesquisar(String nome, String pai, String mae, String dataNascimento) {
        Map<String, List<Pessoa>> indice = pessoasPorNome;
        if (indice == null) {
            return resposta(false, "Os dados do SIGO antigo ainda estão a carregar. Tente novamente dentro de alguns segundos.", null);
        }

        String chaveNome = chave(nome);
        String chavePai = chave(pai);
        String chaveMae = chave(mae);
        if (chaveNome.isEmpty() || chavePai.isEmpty() || chaveMae.isEmpty()) {
            return resposta(false, "Para confirmar a pessoa no SIGO antigo são necessários o nome, o nome do pai e o nome da mãe.", null);
        }
        // Junta os antecedentes de todas as pessoas que coincidem, agrupados por ocorrência (como busca/antecidente)
        Map<String, Map<String, Object>> porOcorrencia = new LinkedHashMap<>();
        Set<Pessoa> encontradas = new LinkedHashSet<>();
        String nomeRecebido = nomePorOrdem(nome);
        String paiPorOrdem = nomePorOrdem(pai);
        String maePorOrdem = nomePorOrdem(mae);
        for (Pessoa p : todasPessoas) {
            if (!p.chaveNome.equals(chaveNome) && !nomesSemelhantes(nomeRecebido, chaveNome, p)) continue;
            if (paisCoincidem(paiPorOrdem, chavePai, maePorOrdem, chaveMae, p)) {
                encontradas.add(p);
            }
        }
        List<Linha> linhas = new ArrayList<>();
        for (Pessoa p : encontradas) {
            linhas.addAll(p.linhas);
        }
        linhas.sort((a, b) -> {
            if (a.dataRegisto == null || b.dataRegisto == null) return a.dataRegisto == null ? (b.dataRegisto == null ? 0 : 1) : -1;
            return b.dataRegisto.compareTo(a.dataRegisto);
        });

        for (Linha l : linhas) {
            Map<String, Object> ant = porOcorrencia.computeIfAbsent(l.numOcorrencia, k -> {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("numOcorrencia", l.numOcorrencia);
                m.put("naturezas", new ArrayList<String>());
                m.put("autoPrincipal", autoPrincipal(l.tpAuto));
                m.put("condicao", condicao(l.tpAuto));
                m.put("tipologiaCrime", new ArrayList<String>());
                m.put("dataRegisto", l.dataRegisto);
                m.put("unidade", l.unidade);
                m.put("fase", l.fase);
                return m;
            });
            adicionarSemRepetir(ant, "naturezas", l.natureza);
            adicionarSemRepetir(ant, "tipologiaCrime", l.tipologia);
        }

        if (porOcorrencia.isEmpty()) {
            return resposta(false, SEM_ANTECEDENTES, null);
        }
        return resposta(true, "Antecedentes de " + nome.trim(), new ArrayList<>(porOcorrencia.values()));
    }

    /** Palavras do nome, sem acentos, em maiúsculas, sem partículas e ordenadas (a ordem dos apelidos não conta). */
    static String chave(String nome) {
        return String.join(" ", new TreeSet<>(palavras(nome)));
    }

    /** As mesmas palavras de chave(), mas pela ordem em que estão escritas. */
    static String nomePorOrdem(String nome) {
        return String.join(" ", palavras(nome));
    }

    private static List<String> palavras(String nome) {
        List<String> lista = new ArrayList<>();
        if (nome == null) return lista;
        String semAcentos = Normalizer.normalize(nome, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        for (String p : semAcentos.toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]+", " ").trim().split(" ")) {
            if (!p.isEmpty() && !PARTICULAS.contains(p)) lista.add(p);
        }
        return lista;
    }

    /** Nome pelo menos 80% igual, comparando pela ordem escrita ou pelas palavras ordenadas (apelidos trocados). */
    private static boolean nomesSemelhantes(String nomeRecebidoPorOrdem, String chaveNomeRecebido, Pessoa p) {
        // Com comprimentos muito diferentes nunca chega a 80%: evita comparar letra a letra com todas as pessoas
        int maior = Math.max(nomeRecebidoPorOrdem.length(), p.nomePorOrdem.length());
        if (Math.abs(nomeRecebidoPorOrdem.length() - p.nomePorOrdem.length()) > maior * (1 - SemelhancaTexto.SEMELHANCA_MINIMA)) {
            return false;
        }
        return SemelhancaTexto.semelhanca(nomeRecebidoPorOrdem, p.nomePorOrdem) >= SemelhancaTexto.SEMELHANCA_MINIMA
                || SemelhancaTexto.semelhanca(chaveNomeRecebido, p.chaveNome) >= SemelhancaTexto.SEMELHANCA_MINIMA;
    }

    /** Pai ou mãe: igual (mesmas palavras) ou pelo menos 80% igual, pela ordem escrita ou pelas palavras ordenadas. */
    private static boolean semelhante(String recebidoPorOrdem, String chaveRecebida, String sigoPorOrdem, String chaveSigo) {
        if (chaveSigo == null || chaveSigo.isEmpty()) return false;
        if (chaveRecebida.equals(chaveSigo)) return true;
        return SemelhancaTexto.semelhanca(recebidoPorOrdem, sigoPorOrdem) >= SemelhancaTexto.SEMELHANCA_MINIMA
                || SemelhancaTexto.semelhanca(chaveRecebida, chaveSigo) >= SemelhancaTexto.SEMELHANCA_MINIMA;
    }

    /**
     * Pais do RNI comparados com os do SIGO antigo: sem nenhum registado no SIGO antigo, aceita; só com um,
     * esse tem de coincidir; com os dois, basta um coincidir.
     */
    private static boolean paisCoincidem(String paiPorOrdem, String chavePai, String maePorOrdem, String chaveMae, Pessoa p) {
        boolean temPai = p.chavePai != null && !p.chavePai.isEmpty();
        boolean temMae = p.chaveMae != null && !p.chaveMae.isEmpty();
        if (!temPai && !temMae) return true;
        return (temPai && semelhante(paiPorOrdem, chavePai, p.paiPorOrdem, p.chavePai))
                || (temMae && semelhante(maePorOrdem, chaveMae, p.maePorOrdem, p.chaveMae));
    }

    @SuppressWarnings("unchecked")
    private static void adicionarSemRepetir(Map<String, Object> ant, String campo, String valor) {
        if (valor == null || valor.isBlank()) return;
        List<String> lista = (List<String>) ant.get(campo);
        if (!lista.contains(valor)) lista.add(valor);
    }

    private static String autoPrincipal(String tpAuto) {
        if (tpAuto == null) return "Outro";
        switch (tpAuto) {
            case "1": return "Auto de Denúncia";
            case "2": return "Auto de Notícia";
            case "3": return "Auto de Detenção";
            case "4": return "Auto de Denúncia VBG";
            case "5": return "Auto de Identificação";
            default: return "Outro";
        }
    }

    // Só existem as três condições que contam como antecedente (ver SQL_ANTECEDENTES)
    private static String condicao(String tpAuto) {
        if ("1".equals(tpAuto)) return "Denunciado";
        if ("3".equals(tpAuto)) return "Detido";
        if ("4".equals(tpAuto)) return "Denunciado/Autor";
        return "Desconhecido";
    }

    private static APIResponse resposta(boolean status, String texto, List<Object> lista) {
        APIResponse.buildAPIResponse b = new APIResponse.buildAPIResponse().setStatus(status).setStatusText(texto);
        if (lista != null) {
            b.setDetails(Collections.<Object>singletonList(lista));
        }
        return b.builder();
    }

    private static final class Pessoa {
        String chaveNome;
        String nomePorOrdem;
        String chavePai;
        String chaveMae;
        String paiPorOrdem;
        String maePorOrdem;
        final List<Linha> linhas = new ArrayList<>();
    }

    private static final class Linha {
        String numOcorrencia;
        LocalDateTime dataRegisto;
        String tpAuto;
        String natureza;
        String tipologia;
        String unidade;
        String fase;
    }
}
