package cv.pn.processmanagement.business.projection;

import java.util.Date;

public interface OcorrenciaBuscaProjection {

    Long getOracleId();
    String getNome();
    String getPai();
    String getMae();
    String getSexo();
    Date getDtNascimento();
    String getNumOcorrencia();
    String getNatureza();
    String getAutoPrincipal();
    String getCondicao();
    String getTipologiaCrime();
    Date getDataRegisto();
    String getUnidade();
    String getFase();
    String getRegistradoPor();
    String getTipo();
    String getEstado();
    String getPrazo();
}
