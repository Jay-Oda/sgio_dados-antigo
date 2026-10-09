package cv.pn.processmanagement.business.sgoOcorrenciaAnexo.services;

import cv.pn.processmanagement.business.sgoOcorrenciaAnexo.dto.SgoOcorrenciaAnexoDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoOcorrenciaAnexoServices {
    APIResponse createAnexo(SgoOcorrenciaAnexoDto dto);
}
