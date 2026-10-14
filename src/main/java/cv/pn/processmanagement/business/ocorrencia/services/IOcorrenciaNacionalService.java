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
            String tipologiaSemelhante,
            String enquadramento,
            Long comando,
            Long unidade,
            String comandoSigla,
            String unidadeSigla,
            String interveniente,
            Integer offset);

    Map<String, Object> obterOpcoes();

    APIResponse obterHistorico(String numOcorrencia);

    Map<String, Object> estadoCache();
}