package cv.pn.processmanagement.business.projection;

import java.util.Date;

public interface OcorrenciaHistoricoProjection {

    String getNumOcorrencia();
    String getDescricaoEtapa();
    Date getDataRegisto();
    String getRegistradoPor();
    String getDataAnalise();
    String getUserAnalise();
    String getTipoAnalise();
    String getObsAnalise();
    String getDataDespacho();
    String getUserDespacho();
    String getTipoDespacho();
    String getObsDespacho();
    String getDataEnviado();
    String getUserEnviado();
    String getDataEnvioSij();
    String getDataArquiva();
    String getUserArquiva();
    String getMotivoArquiva();
    String getDataFecho();
    String getUserFecho();
    String getTipoFecho();
}
