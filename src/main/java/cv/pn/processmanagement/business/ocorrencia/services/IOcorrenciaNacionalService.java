package cv.pn.processmanagement.business.ocorrencia.services;

import cv.pn.processmanagement.utilities.APIResponse;

import java.util.Map;

public interface IOcorrenciaNacionalService {

    APIResponse listarOcorrencias(
            int page,
            int size,
            String numOcorrencia,
            String tpAuto,
            String estado,
            String dataInicio,
            String dataFim,
            String natureza,
            String tipologia,
            String enquadramento,
            Long comando,
            Long unidade);

    Map<String, Object> obterOpcoes();
}