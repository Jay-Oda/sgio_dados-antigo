package cv.pn.processmanagement.business.sgoOcorrenciaRisco.services;

import cv.pn.processmanagement.business.sgoOcorrenciaRisco.dto.SgoOcorrenciaRiscoDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoOcorrenciaRiscoService {
    APIResponse createRisco(SgoOcorrenciaRiscoDto dto);
}
