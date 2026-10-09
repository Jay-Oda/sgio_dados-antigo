package cv.pn.processmanagement.business.crimeOcorrencia.services;

import cv.pn.processmanagement.business.crimeOcorrencia.dto.TipoCrimeTipoOcorrenciaDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ITipoCrimeTipoOcorrenciaService {
    APIResponse createTipoCrime(TipoCrimeTipoOcorrenciaDto dto);
}
