package cv.pn.processmanagement.business.projection;

import java.util.Date;

public interface PessoaOcorrenciaProjection {

    Long getOracleIdPessoa();

    String getNome();

    String getNum();

    String getCondicao();

    Long getOracleIdOcorrencia();

    String getNumOcorrencia();

    Date getDataRegisto();
}
