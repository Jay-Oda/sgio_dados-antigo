package cv.pn.processmanagement.sgoPecaPessoa.service;

import cv.pn.processmanagement.sgoPecaPessoa.dto.SgoPecaPessoaDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISgoPecaPessoaService {
    APIResponse createSgoPecaPessoa(SgoPecaPessoaDto dto);
}
