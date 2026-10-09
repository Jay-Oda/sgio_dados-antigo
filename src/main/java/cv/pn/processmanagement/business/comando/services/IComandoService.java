package cv.pn.processmanagement.business.comando.services;

import cv.pn.processmanagement.business.comando.dto.ComandoDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface IComandoService {
    APIResponse createComando(ComandoDto dto);
}
