package cv.pn.processmanagement.business.tipoAuto.services;

import cv.pn.processmanagement.business.tipoAuto.dto.TiposAutoDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ITiposAutoService {
    APIResponse createTiposAuto(TiposAutoDto dto);
}
