package cv.pn.processmanagement.business.projection;

import java.util.Date;

public interface PessoaResumoProjection {

    Long getId();

    Long getOracleId();

    String getNome();

    String getAlcunhas();

    String getNum();

    String getPai();

    String getMae();

    Date getDtNascimento();
}
