package cv.pn.processmanagement.business.processo.services;

import cv.pn.processmanagement.business.processo.dto.ProcessoDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface IProcessoService {
    APIResponse createProcesso(ProcessoDto dto);
}
