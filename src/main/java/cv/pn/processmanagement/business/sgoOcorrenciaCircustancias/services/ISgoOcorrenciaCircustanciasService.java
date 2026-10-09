package cv.pn.processmanagement.business.sgoOcorrenciaCircustancias.services;

import cv.pn.processmanagement.business.sgoOcorrenciaCircustancias.dto.SgoOcorrenciaCircustanciasDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoOcorrenciaCircustanciasService {
    APIResponse createCircustancias(SgoOcorrenciaCircustanciasDto dto);
}
