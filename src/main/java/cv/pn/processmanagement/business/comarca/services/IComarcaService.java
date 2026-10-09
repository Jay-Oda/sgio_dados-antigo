package cv.pn.processmanagement.business.comarca.services;


import cv.pn.processmanagement.business.comarca.dto.ComarcaDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface IComarcaService {
    APIResponse createComarca(ComarcaDto dto);


}
