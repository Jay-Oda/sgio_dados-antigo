package cv.pn.processmanagement.business.sgoIntervenente.model.service;

import cv.pn.processmanagement.business.sgoIntervenente.model.SgoPecaIntervenente;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoPecaIntervenenteService {
    APIResponse createSgoPecaInter(SgoPecaIntervenente dto);
}
