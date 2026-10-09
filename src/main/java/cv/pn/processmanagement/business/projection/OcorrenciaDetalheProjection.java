package cv.pn.processmanagement.business.projection;

import java.util.Date;

public interface OcorrenciaDetalheProjection {

    Long getOracleIdOcorrencia();
    String getNumOcorrencia();
    String getNatureza();
    String getAutoPrincipal();
    String getUnidade();
    String getComando();
    String getFase();
    Date getDataRegisto();
    String getMotivo();
    String getPontoReferencia();
    String getLocal();
    String getAutuante();
    String getCategoriaAutuante();
}
