package cv.pn.processmanagement.business.pessoaEnviado.services;

import cv.pn.processmanagement.business.pessoaEnviado.dto.PessoaEnviadoDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface IPessoaEnviadoService {
    APIResponse createPessoaEnviado(PessoaEnviadoDto dto);
}
