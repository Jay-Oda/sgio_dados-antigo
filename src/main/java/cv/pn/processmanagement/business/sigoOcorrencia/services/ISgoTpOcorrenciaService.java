package cv.pn.processmanagement.business.sigoOcorrencia.services;

import cv.pn.processmanagement.business.sigoOcorrencia.dto.SgoTpOcorrenciaDTO;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoTpOcorrenciaService {
    APIResponse createSgo(SgoTpOcorrenciaDTO dto);
}
