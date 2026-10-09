package cv.pn.processmanagement.business.esquadra.services;

import cv.pn.processmanagement.business.esquadra.dto.EsquadraDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface IEsquadraService {
    APIResponse createEsquadra(EsquadraDto dto);
}
