package cv.pn.processmanagement.business.sigoOcorrencia.services;


import cv.pn.processmanagement.business.sigoOcorrencia.dto.SgoTpOcorrenciaDTO;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.sigoOcorrencia.model.SgoTpOcorrencia;
import cv.pn.processmanagement.business.sigoOcorrencia.repository.SgoTpOcorrenciaRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SgoTpOcorrenciaService implements ISgoTpOcorrenciaService {

    private final SgoTpOcorrenciaRepository sgoTpOcorrenciaRepository;

    public SgoTpOcorrenciaService(SgoTpOcorrenciaRepository sgoTpOcorrenciaRepository) {
        this.sgoTpOcorrenciaRepository = sgoTpOcorrenciaRepository;
    }

    @Override
    public APIResponse createSgo(SgoTpOcorrenciaDTO dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

       try {


           SgoTpOcorrencia sgoTpOcorrencia = new SgoTpOcorrencia();
            BeanUtils.copyProperties(dto, sgoTpOcorrencia);
            sgoTpOcorrencia.setUserCreate("ADMIN");



            sgoTpOcorrenciaRepository.save(sgoTpOcorrencia);


            return new APIResponse.buildAPIResponse()
                   .setStatus(true)
                    .setStatusText("Ocorrencia criada com sucesso")
                    .setDetails(Collections.singletonList(sgoTpOcorrencia))
                    .builder();

        } catch (Exception e) {

            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar sgoTpOcorrencia")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
       }

    }


}
