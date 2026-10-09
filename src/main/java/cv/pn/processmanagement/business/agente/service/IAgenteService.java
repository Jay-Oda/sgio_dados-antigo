package cv.pn.processmanagement.business.agente.service;

import cv.pn.processmanagement.business.agente.dto.AgenteDto;
import cv.pn.processmanagement.utilities.APIResponse;

public interface IAgenteService {
    APIResponse createAgente(AgenteDto dto);

    APIResponse createAgenteParams(String numero, String nome);

    APIResponse buscarPorNumero(String numero);

    APIResponse buscarPorNomeExato(String nome);

    APIResponse buscarPorId(String id);

    APIResponse listarQueixasDoAgente(String agenteId, boolean todas);

    APIResponse listarOcorrenciasDoAgente(String agenteId, String auto);

    APIResponse listarOcorrenciasEnvolvimentoDoAgente(String agenteId, String auto, String condicao);

    APIResponse listarPessoasDasOcorrenciasDoAgente(String agenteId);

    APIResponse listarOcorrenciasContraAgente(String agenteId, String auto);
}
