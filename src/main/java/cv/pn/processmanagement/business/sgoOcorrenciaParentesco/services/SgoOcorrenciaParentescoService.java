package cv.pn.processmanagement.business.sgoOcorrenciaParentesco.services;

import cv.pn.processmanagement.business.sgoOcorrenciaParentesco.dto.SgoOcorrenciaParentescoDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.sgoOcorrenciaParentesco.model.SgoOcorrenciaParentesco;
import cv.pn.processmanagement.business.sgoOcorrenciaParentesco.repository.SgoOcorrenciaParentescoRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SgoOcorrenciaParentescoService implements ISgoOcorrenciaParentescoService{

    private final SgoOcorrenciaParentescoRepository sgoOcorrenciaParentescoRepository;

    public SgoOcorrenciaParentescoService(SgoOcorrenciaParentescoRepository sgoOcorrenciaParentescoRepository) {
        this.sgoOcorrenciaParentescoRepository = sgoOcorrenciaParentescoRepository;
    }

    @Override
    public APIResponse createParentesco(SgoOcorrenciaParentescoDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            SgoOcorrenciaParentesco sgoOcorrenciaParentesco = new SgoOcorrenciaParentesco();
            BeanUtils.copyProperties(dto, sgoOcorrenciaParentesco);
            sgoOcorrenciaParentesco.setUserCreate("ADMIN");

            sgoOcorrenciaParentescoRepository.save(sgoOcorrenciaParentesco);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Ocorrencia Parentesco criada com sucesso")
                    .setDetails(Collections.singletonList(sgoOcorrenciaParentesco))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Ocorrencia Parentesco")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
