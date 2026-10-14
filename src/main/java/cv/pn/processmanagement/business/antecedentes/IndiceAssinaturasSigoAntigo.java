package cv.pn.processmanagement.business.antecedentes;

import cv.pn.processmanagement.business.CargaSigoAntigo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Índice em memória para o detalhe da ocorrência do SIGO antigo.
 *
 * As tabelas assinatura (~200 mil linhas, 1,4 GB com as imagens) e sigo_pessoa (~276 mil) não têm índice
 * em id_pessoa, id_agente nem nome, e não se pode mexer na base. Sem isto, cada detalhe lia-as inteiras
 * (4 a 11 s). Aqui guarda-se só quem tem que assinatura (sem a imagem) e que pessoas têm cada nome; as
 * queries do detalhe recebem esses candidatos e leem-nos pela chave primária. As condições do SQL não
 * mudam: os candidatos são sempre um superconjunto (nome comparado sem acentos, maiúsculas nem espaços).
 */
@Component
public class IndiceAssinaturasSigoAntigo {

    private static final Logger LOG = LoggerFactory.getLogger(IndiceAssinaturasSigoAntigo.class);
    private static final long INTERVALO_ATUALIZACAO_MINUTOS = 60;

    private static final String SQL_ASSINATURAS = "SELECT id, id_pessoa, id_agente, nome FROM assinatura";
    private static final String SQL_PESSOAS = "SELECT oracle_id, nome FROM sigo_pessoa WHERE oracle_id IS NOT NULL AND nome IS NOT NULL";

    private final JdbcTemplate jdbcTemplate;
    private final ScheduledExecutorService agendador = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "indice-assinaturas");
        t.setDaemon(true);
        return t;
    });

    private volatile Indice indice;
    private volatile java.util.Date carregadoEm;
    private volatile long duracaoUltimaCargaMs;
    private volatile String ultimoErro;

    public IndiceAssinaturasSigoAntigo(JdbcTemplate jdbcTemplate) {
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

    public boolean pronto() {
        return indice != null;
    }

    public Map<String, Object> estado() {
        Indice i = indice;
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("pronto", i != null);
        m.put("assinaturas", i == null ? 0 : i.totalAssinaturas);
        m.put("pessoas", i == null ? 0 : i.totalPessoas);
        m.put("carregadoEm", carregadoEm);
        m.put("duracaoUltimaCargaMs", duracaoUltimaCargaMs);
        m.put("atualizacaoMinutos", INTERVALO_ATUALIZACAO_MINUTOS);
        m.put("ultimoErro", ultimoErro);
        return m;
    }

    /** Ids das assinaturas destas pessoas (assinatura.id_pessoa) e deste agente (assinatura.id_agente). */
    public List<String> assinaturas(Collection<Long> idsPessoa, Long idAgente) {
        Indice i = indice;
        Set<String> ids = new LinkedHashSet<>();
        for (Long id : idsPessoa) {
            ids.addAll(i.assinaturasPorPessoa.getOrDefault(id, Collections.emptyList()));
        }
        if (idAgente != null) {
            for (Assinatura a : i.assinaturasPorAgente.getOrDefault(idAgente, Collections.emptyList())) {
                ids.add(a.id);
            }
        }
        return new ArrayList<>(ids);
    }

    /** sigo_pessoa.oracle_id das pessoas com o mesmo nome que as assinaturas deste agente. */
    public List<Long> pessoasComNomeDoAgente(Long idAgente) {
        Indice i = indice;
        Set<Long> ids = new LinkedHashSet<>();
        if (idAgente != null) {
            for (Assinatura a : i.assinaturasPorAgente.getOrDefault(idAgente, Collections.emptyList())) {
                ids.addAll(i.pessoasPorNome.getOrDefault(a.chaveNome, Collections.emptyList()));
            }
        }
        return new ArrayList<>(ids);
    }

    /** Ids das assinaturas com este nome. */
    public List<String> assinaturasPorNome(String nome) {
        return new ArrayList<>(indice.assinaturasPorNome.getOrDefault(chave(nome), Collections.emptyList()));
    }

    /** Nome sem acentos, maiúsculas nem espaços: dois nomes iguais para o UPPER do PostgreSQL têm a mesma chave. */
    static String chave(String nome) {
        if (nome == null) return "";
        String semAcentos = Normalizer.normalize(nome, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return semAcentos.toUpperCase(Locale.ROOT).replaceAll("\\s+", "");
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
            Indice novo = new Indice();
            CargaSigoAntigo.consultarEmBlocos(jdbcTemplate, SQL_ASSINATURAS, rs -> {
                String id = rs.getString("id");
                long idPessoa = rs.getLong("id_pessoa");
                boolean temPessoa = !rs.wasNull();
                long idAgente = rs.getLong("id_agente");
                boolean temAgente = !rs.wasNull();
                String chaveNome = chave(rs.getString("nome"));
                if (temPessoa) {
                    novo.assinaturasPorPessoa.computeIfAbsent(idPessoa, k -> new ArrayList<>(1)).add(id);
                }
                if (temAgente) {
                    novo.assinaturasPorAgente.computeIfAbsent(idAgente, k -> new ArrayList<>()).add(new Assinatura(id, chaveNome));
                }
                if (!chaveNome.isEmpty()) {
                    novo.assinaturasPorNome.computeIfAbsent(chaveNome, k -> new ArrayList<>(1)).add(id);
                }
                novo.totalAssinaturas++;
            });
            CargaSigoAntigo.consultarEmBlocos(jdbcTemplate, SQL_PESSOAS, rs -> {
                String chaveNome = chave(rs.getString("nome"));
                if (!chaveNome.isEmpty()) {
                    novo.pessoasPorNome.computeIfAbsent(chaveNome, k -> new ArrayList<>(1)).add(rs.getLong("oracle_id"));
                }
                novo.totalPessoas++;
            });
            indice = novo;
            carregadoEm = new java.util.Date();
            duracaoUltimaCargaMs = System.currentTimeMillis() - inicio;
            ultimoErro = null;
            LOG.info("Índice de assinaturas (SIGO antigo) carregado: {} assinaturas, {} pessoas em {} ms",
                    novo.totalAssinaturas, novo.totalPessoas, duracaoUltimaCargaMs);
        } catch (Exception e) {
            ultimoErro = e.getMessage();
            LOG.error("Falha ao carregar o índice de assinaturas do SIGO antigo; o detalhe continua a ler as tabelas inteiras e há nova tentativa dentro de 1 minuto", e);
            agendador.schedule(this::carregarSemFalhar, 1, TimeUnit.MINUTES);
        }
    }

    private static final class Assinatura {
        final String id;
        final String chaveNome;

        Assinatura(String id, String chaveNome) {
            this.id = id;
            this.chaveNome = chaveNome;
        }
    }

    private static final class Indice {
        final Map<Long, List<String>> assinaturasPorPessoa = new HashMap<>();
        final Map<Long, List<Assinatura>> assinaturasPorAgente = new HashMap<>();
        final Map<String, List<String>> assinaturasPorNome = new HashMap<>();
        final Map<String, List<Long>> pessoasPorNome = new HashMap<>();
        int totalAssinaturas;
        int totalPessoas;
    }
}
