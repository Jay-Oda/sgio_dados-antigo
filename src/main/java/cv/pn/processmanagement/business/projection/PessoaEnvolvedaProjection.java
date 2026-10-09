package cv.pn.processmanagement.business.projection;

import java.util.Date;

public interface PessoaEnvolvedaProjection {

    Long getOracleId();
    String getNome();
    String getAlcunhas();
    String getPai();
    String getMae();
    String getCondicao();
    Date getDtNascimento();
    String getIdade();
    String getEstadoCivil();
    String getContacto();
    String getMorada();
    String getNacionalidade();
    String getNaturalidade();
    String getProfissao();
    String getCategoriaEfetivo();
    String getEsquadraEfetivo();
    String getTipoAssinante();
    String getCategoriaAssinatura();
    String getAssina();
    String getSabeAssinar();
    String getAssinatura();
}
