package cv.pn.processmanagement.business.sgoOcorrenciaAnexos.services;


import cv.pn.processmanagement.business.sgoOcorrenciaAnexos.dto.SgoOcorrenciaAnexosDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.sgoOcorrenciaAnexos.model.SgoOcorrenciaAnexos;
import cv.pn.processmanagement.business.sgoOcorrenciaAnexos.repository.SgoOcorrenciaAnexosRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SgoOcorrenciaAnexosService implements ISgoOcorrenciaAnexosService{

    private final SgoOcorrenciaAnexosRepository sgoOcorrenciaAnexosRepository;

    public SgoOcorrenciaAnexosService(SgoOcorrenciaAnexosRepository sgoOcorrenciaAnexosRepository) {
        this.sgoOcorrenciaAnexosRepository = sgoOcorrenciaAnexosRepository;
    }

    @Override
    public APIResponse createAnexos(SgoOcorrenciaAnexosDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            SgoOcorrenciaAnexos sgoOcorrenciaAnexos = new SgoOcorrenciaAnexos();
            BeanUtils.copyProperties(dto, sgoOcorrenciaAnexos);
            sgoOcorrenciaAnexos.setUserCreate("ADMIN");

            sgoOcorrenciaAnexosRepository.save(sgoOcorrenciaAnexos);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Ocorrencia Anexos criada com sucesso")
                    .setDetails(Collections.singletonList(sgoOcorrenciaAnexos))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Ocorrencia Anexos")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
