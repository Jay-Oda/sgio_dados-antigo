package cv.pn.processmanagement.business.sgoOcorrenciaAnexo.services;


import cv.pn.processmanagement.business.sgoOcorrenciaAnexo.dto.SgoOcorrenciaAnexoDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.sgoOcorrenciaAnexo.model.SgoOcorrenciaAnexo;
import cv.pn.processmanagement.business.sgoOcorrenciaAnexo.repository.SgoOcorrenciaAnexoRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SgoOcorrenciaAnexoService implements ISgoOcorrenciaAnexoServices{

    private final SgoOcorrenciaAnexoRepository sgoOcorrenciaAnexoRepository;

    public SgoOcorrenciaAnexoService(SgoOcorrenciaAnexoRepository sgoOcorrenciaAnexoRepository) {
        this.sgoOcorrenciaAnexoRepository = sgoOcorrenciaAnexoRepository;
    }

    @Override
    public APIResponse createAnexo(SgoOcorrenciaAnexoDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            SgoOcorrenciaAnexo sgoOcorrenciaAnexo = new SgoOcorrenciaAnexo();
            BeanUtils.copyProperties(dto, sgoOcorrenciaAnexo);
            sgoOcorrenciaAnexo.setUserCreate("ADMIN");

            sgoOcorrenciaAnexoRepository.save(sgoOcorrenciaAnexo);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Ocorrencia Anexo criada com sucesso")
                    .setDetails(Collections.singletonList(sgoOcorrenciaAnexo))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Ocorrencia Anexo")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
