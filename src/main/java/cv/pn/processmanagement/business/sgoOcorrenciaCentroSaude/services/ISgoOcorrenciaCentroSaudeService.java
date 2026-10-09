package cv.pn.processmanagement.business.sgoOcorrenciaCentroSaude.services;

import cv.pn.processmanagement.business.sgoOcorrenciaCentroSaude.dto.SgoOcorrenciaCentroSaudeDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoOcorrenciaCentroSaudeService {
    APIResponse createCentroSaude(SgoOcorrenciaCentroSaudeDto dto);
}
