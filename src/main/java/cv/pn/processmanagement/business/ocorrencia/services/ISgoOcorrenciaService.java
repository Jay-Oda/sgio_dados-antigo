package cv.pn.processmanagement.business.ocorrencia.services;

import cv.pn.processmanagement.business.ocorrencia.dto.SgoOcorrenciaDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoOcorrenciaService {
    APIResponse createOcorrencia(SgoOcorrenciaDto dto);

    Object consultarMotivo(String num);
}
