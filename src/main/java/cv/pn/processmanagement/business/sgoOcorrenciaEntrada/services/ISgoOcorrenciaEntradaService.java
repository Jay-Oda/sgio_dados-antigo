package cv.pn.processmanagement.business.sgoOcorrenciaEntrada.services;

import cv.pn.processmanagement.business.sgoOcorrenciaEntrada.dto.SgoOcorrenciaEntradaDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoOcorrenciaEntradaService {
    APIResponse createEntrada(SgoOcorrenciaEntradaDto dto);
}
