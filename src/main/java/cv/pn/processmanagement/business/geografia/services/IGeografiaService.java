package cv.pn.processmanagement.business.geografia.services;

import cv.pn.processmanagement.business.geografia.dto.GeografiaDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface IGeografiaService {
    APIResponse createGeografia(GeografiaDto dto);
}
