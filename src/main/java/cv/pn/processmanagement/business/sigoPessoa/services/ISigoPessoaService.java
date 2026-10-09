package cv.pn.processmanagement.business.sigoPessoa.services;

import cv.pn.processmanagement.business.sigoPessoa.PessoaFiltroRequest;
import cv.pn.processmanagement.business.sigoPessoa.dto.SigoPessoaDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface ISigoPessoaService {
    APIResponse createSigoPessoa(SigoPessoaDto dto);

    APIResponse pesquisarPessoaComFiltro(PessoaFiltroRequest req);
}
