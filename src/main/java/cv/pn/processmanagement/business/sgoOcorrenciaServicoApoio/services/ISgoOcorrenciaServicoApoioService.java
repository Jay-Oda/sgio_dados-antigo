package cv.pn.processmanagement.business.sgoOcorrenciaServicoApoio.services;

import cv.pn.processmanagement.business.sgoOcorrenciaServicoApoio.dto.SgoOcorrenciaServicoApoioDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoOcorrenciaServicoApoioService {
    APIResponse createServicoApoio(SgoOcorrenciaServicoApoioDto dto);
}
