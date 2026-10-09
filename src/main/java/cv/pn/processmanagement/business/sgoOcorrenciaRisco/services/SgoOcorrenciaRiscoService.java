package cv.pn.processmanagement.business.sgoOcorrenciaRisco.services;


import cv.pn.processmanagement.business.sgoOcorrenciaRisco.dto.SgoOcorrenciaRiscoDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.sgoOcorrenciaRisco.model.SgoOcorrenciaRisco;
import cv.pn.processmanagement.business.sgoOcorrenciaRisco.repository.SgoOcorrenciaRiscoRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SgoOcorrenciaRiscoService implements ISgoOcorrenciaRiscoService{

    private final SgoOcorrenciaRiscoRepository sgoOcorrenciaRiscoRepository;

    public SgoOcorrenciaRiscoService(SgoOcorrenciaRiscoRepository sgoOcorrenciaRiscoRepository) {
        this.sgoOcorrenciaRiscoRepository = sgoOcorrenciaRiscoRepository;
    }

    @Override
    public APIResponse createRisco(SgoOcorrenciaRiscoDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            SgoOcorrenciaRisco sgoOcorrenciaRisco = new SgoOcorrenciaRisco();
            BeanUtils.copyProperties(dto, sgoOcorrenciaRisco);
            sgoOcorrenciaRisco.setUserCreate("ADMIN");

            sgoOcorrenciaRiscoRepository.save(sgoOcorrenciaRisco);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Ocorrencia Risco criada com sucesso")
                    .setDetails(Collections.singletonList(sgoOcorrenciaRisco))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Ocorrencia Risco")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
