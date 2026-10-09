package cv.pn.processmanagement.business.sgoOcorrenciaParentesco.services;

import cv.pn.processmanagement.business.sgoOcorrenciaParentesco.dto.SgoOcorrenciaParentescoDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoOcorrenciaParentescoService{
    APIResponse createParentesco(SgoOcorrenciaParentescoDto dto);
}
