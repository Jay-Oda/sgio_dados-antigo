package cv.pn.processmanagement.business.intervinientes.services;

import cv.pn.processmanagement.business.intervinientes.dto.IntervenientesDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface IIntervenientesService {
    APIResponse createIntervinientes(IntervenientesDto dto);
}
