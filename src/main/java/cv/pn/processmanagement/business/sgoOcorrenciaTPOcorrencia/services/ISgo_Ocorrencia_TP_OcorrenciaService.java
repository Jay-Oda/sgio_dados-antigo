package cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.services;

import cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.dto.Sgo_Ocorrencia_TP_OcorrenciaDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgo_Ocorrencia_TP_OcorrenciaService{
    APIResponse createSgoTP(Sgo_Ocorrencia_TP_OcorrenciaDto dto);
}
