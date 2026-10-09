package cv.pn.processmanagement.business.projection;

import java.util.Date;

public interface OcorrenciaNacionalProjection {

    Long getOracleIdOcorrencia();
    String getNumOcorrencia();
    Date getDataRegisto();
    String getTpAuto();
    String getAutoPrincipal();
    String getNumProcesso();
    String getUnidade();
    String getComando();
    String getFase();
    String getEstado();
    String getEstadoCodigo();
    String getRegistradoPor();
    String getPrazo();
    String getTipo();
    String getNaturezas();
    String getTipologias();
}