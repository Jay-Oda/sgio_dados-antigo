package cv.pn.processmanagement.business.projection;

import java.util.Date;

public interface AgenteOcorrenciaProjection {

    Long getOracleId();

    String getNumOcorrencia();

    Date getDataRegisto();

    String getNatureza();

    String getAutoPrincipal();

    String getUnidade();

    String getFase();

    String getEstado();

    String getMotivo();

    String getCondicao();

    String getTpAuto();

    String getNumProcesso();

    String getPontoReferencia();
}
