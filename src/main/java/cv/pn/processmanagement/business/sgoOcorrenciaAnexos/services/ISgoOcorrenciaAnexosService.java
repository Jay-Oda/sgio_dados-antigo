package cv.pn.processmanagement.business.sgoOcorrenciaAnexos.services;

import cv.pn.processmanagement.business.sgoOcorrenciaAnexos.dto.SgoOcorrenciaAnexosDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoOcorrenciaAnexosService {
    APIResponse createAnexos(SgoOcorrenciaAnexosDto dto);
}
