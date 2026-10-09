package cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.service;

import cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.dto.SgoHistoricoVitimaVbgDTO;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoHistoricoVitimaVbgService {
    APIResponse createVitimaVbg(SgoHistoricoVitimaVbgDTO dto);
}
