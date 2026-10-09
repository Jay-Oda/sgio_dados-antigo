package cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.sgoHistoricoAutorVbg.service;

import cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.sgoHistoricoAutorVbg.dto.SgoHistoricoAutorVbgDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoHistoricoAutorVbgService {
    APIResponse createAutorVbg(SgoHistoricoAutorVbgDto dto);
}
