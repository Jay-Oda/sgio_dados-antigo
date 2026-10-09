package cv.pn.processmanagement.business.projection;

import java.util.Date;

public interface PessoaDetalheProjection {

    Long getId();

    Long getOracleId();

    String getNome();

    String getAlcunhas();

    String getNum();

    String getPai();

    String getMae();

    Date getDtNascimento();

    String getSexo();

    String getEstadoCivil();

    String getTpIdentificacao();

    String getNaturalidade();

    String getMorada();

    String getNacionalidade();

    String getContacto();

    String getProfissao();

    String getTipoPessoa();

    String getHabilitacaoLiteraria();

    String getIdade();
}
