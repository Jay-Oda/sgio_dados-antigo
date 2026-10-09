package cv.pn.processmanagement.business.sgoOcorrenciaMeios.services;

import cv.pn.processmanagement.business.sgoOcorrenciaMeios.dto.SgoOcorrenciaMeiosDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoOcorrenciaMeiosService {
    APIResponse createEstado(SgoOcorrenciaMeiosDto dto);
}
