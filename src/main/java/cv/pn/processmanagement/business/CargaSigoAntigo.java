package cv.pn.processmanagement.business;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Leitura das tabelas grandes do SIGO antigo pelas caches em memória (listagem nacional e antecedentes),
 * ao arrancar e de hora a hora.
 *
 * Os servidores PostgreSQL de teste (10.72.208.91:32252 e :32041, pouca memória) vão abaixo ("recovery mode")
 * quando uma destas queries é pedida de uma só vez, e quando as duas cargas correm ao mesmo tempo. Lida em
 * blocos (cursor) e uma carga de cada vez, a mesma query funciona.
 */
public final class CargaSigoAntigo {

    public static final ReentrantLock UMA_DE_CADA_VEZ = new ReentrantLock(true);

    private static final int LINHAS_POR_BLOCO = 2000;

    private CargaSigoAntigo() {
    }

    /** Executa a query lendo o resultado em blocos (o PostgreSQL só usa cursor fora do modo autocommit). */
    public static void consultarEmBlocos(JdbcTemplate jdbcTemplate, String sql, RowCallbackHandler linha) {
        jdbcTemplate.execute((ConnectionCallback<Void>) con -> {
            boolean autoCommit = con.getAutoCommit();
            con.setAutoCommit(false);
            try (Statement st = con.createStatement()) {
                st.setFetchSize(LINHAS_POR_BLOCO);
                try (ResultSet rs = st.executeQuery(sql)) {
                    while (rs.next()) {
                        linha.processRow(rs);
                    }
                }
                con.commit();
            } catch (SQLException | RuntimeException e) {
                try {
                    con.rollback();
                } catch (SQLException ignorar) {
                    // A ligação pode já ter caído; o erro original é o que interessa
                }
                throw e;
            } finally {
                con.setAutoCommit(autoCommit);
            }
            return null;
        });
    }
}
