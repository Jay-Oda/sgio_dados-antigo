package cv.pn.processmanagement.business.assinatura.services;

import cv.pn.processmanagement.business.assinatura.dto.AssinaturaDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface IAssinaturaService {
    APIResponse createAssinatura(AssinaturaDto dto);
}
