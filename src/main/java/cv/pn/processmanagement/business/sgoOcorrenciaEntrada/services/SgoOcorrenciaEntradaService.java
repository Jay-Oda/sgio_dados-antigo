package cv.pn.processmanagement.business.sgoOcorrenciaEntrada.services;


import cv.pn.processmanagement.business.sgoOcorrenciaEntrada.dto.SgoOcorrenciaEntradaDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.sgoOcorrenciaEntrada.model.SgoOcorrenciaEntrada;
import cv.pn.processmanagement.business.sgoOcorrenciaEntrada.repository.SgoOcorrenciaEntradaRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SgoOcorrenciaEntradaService implements ISgoOcorrenciaEntradaService {

    private final SgoOcorrenciaEntradaRepository sgoOcorrenciaEntradaRepository;

    public SgoOcorrenciaEntradaService(SgoOcorrenciaEntradaRepository sgoOcorrenciaEntradaRepository) {
        this.sgoOcorrenciaEntradaRepository = sgoOcorrenciaEntradaRepository;
    }

    @Override
    public APIResponse createEntrada(SgoOcorrenciaEntradaDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            SgoOcorrenciaEntrada sgoOcorrenciaEntrada = new SgoOcorrenciaEntrada();
            BeanUtils.copyProperties(dto, sgoOcorrenciaEntrada);
            sgoOcorrenciaEntrada.setUserCreate("ADMIN");

            sgoOcorrenciaEntradaRepository.save(sgoOcorrenciaEntrada);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Ocorrencia Entrada criada com sucesso")
                    .setDetails(Collections.singletonList(sgoOcorrenciaEntrada))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Ocorrencia Entrada")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
