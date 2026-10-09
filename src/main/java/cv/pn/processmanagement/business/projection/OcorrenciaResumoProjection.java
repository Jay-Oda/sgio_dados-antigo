package cv.pn.processmanagement.business.projection;

import java.util.Date;

public interface OcorrenciaResumoProjection {

    String getNumOcorrencia();
    String getNatureza();          // sgo_ocorrencia.NUM_OCORRENCIA
    String getAutoPrincipal();
    String getCondicao();          // sgo_ocorrencia_pessoa.CONDICAO
    String getUnidade();           // esquadra
    String getFase();              // estado / fase do processo
    Date getDataRegisto();         // data da ocorrência
    Long getOracleIdOcorrencia();  // sgo_ocorrencia.ORACLE_ID
    String getMotivo();            // sgo_ocorrencia.MOTIVO (narrativa dos factos)
    String getPontoReferencia();   // sgo_ocorrencia.PONTO_REFERENCIA
    String getAutuante();          // nome do agente autuante (sigo_pessoa via sgo_ocorrencia.id_agente)
}
