package cv.pn.processmanagement.business.sigoOcorrenciaPessoa.services;

import cv.pn.processmanagement.business.projection.dto.OcorrenciaResumoDto;
import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.dto.SgoOcorrenciaPessoaDto;
import cv.pn.processmanagement.utilities.APIResponse;

import java.util.List;

public interface ISgoOcorrenciaPessoaServices {
    APIResponse createOcorrenciaPessoa(SgoOcorrenciaPessoaDto dto);

    //List<SgoOcorrenciaPessoa> pesquisarPessoaEmOcorrencia(SearchPessoaOcorrenciaDto dto);

    //ExisteOcorrenciaResponseDto pessoaTemOcorrencia(SearchPessoaOcorrenciaDto dto);

    //APIResponse verificarAssociacaoPorNome(String nome);

   // List<OcorrenciaResumoDto> listarOcorrenciasDaPessoa(Long oracleId);
}
