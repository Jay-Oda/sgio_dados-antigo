package cv.pn.processmanagement.business.sgoOcorrenciaCircustancias.services;

import cv.pn.processmanagement.business.sgoOcorrenciaCircustancias.dto.SgoOcorrenciaCircustanciasDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.sgoOcorrenciaCircustancias.model.SgoOcorrenciaCircustancias;
import cv.pn.processmanagement.business.sgoOcorrenciaCircustancias.repository.SgoOcorrenciaCircustanciasRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SgoOcorrenciaCircustanciasService implements ISgoOcorrenciaCircustanciasService{

    private final SgoOcorrenciaCircustanciasRepository sgoOcorrenciaCircustanciasRepository;

    public SgoOcorrenciaCircustanciasService(SgoOcorrenciaCircustanciasRepository sgoOcorrenciaCircustanciasRepository) {
        this.sgoOcorrenciaCircustanciasRepository = sgoOcorrenciaCircustanciasRepository;
    }

    @Override
    public APIResponse createCircustancias(SgoOcorrenciaCircustanciasDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            SgoOcorrenciaCircustancias sgoOcorrenciaCircustancias = new SgoOcorrenciaCircustancias();
            BeanUtils.copyProperties(dto, sgoOcorrenciaCircustancias);
            sgoOcorrenciaCircustancias.setUserCreate("ADMIN");

            sgoOcorrenciaCircustanciasRepository.save(sgoOcorrenciaCircustancias);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Ocorrencia Circustancias criada com sucesso")
                    .setDetails(Collections.singletonList(sgoOcorrenciaCircustancias))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Ocorrencia Circustancias")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
