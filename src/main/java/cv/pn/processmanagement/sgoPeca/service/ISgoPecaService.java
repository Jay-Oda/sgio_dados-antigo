package cv.pn.processmanagement.sgoPeca.service;

import cv.pn.processmanagement.sgoPeca.dto.SgoPecaDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoPecaService {
    APIResponse createSgoPeca(SgoPecaDto dto);
}
