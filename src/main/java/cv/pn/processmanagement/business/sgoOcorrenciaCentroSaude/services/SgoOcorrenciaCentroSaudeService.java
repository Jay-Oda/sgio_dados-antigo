package cv.pn.processmanagement.business.sgoOcorrenciaCentroSaude.services;


import cv.pn.processmanagement.business.sgoOcorrenciaCentroSaude.dto.SgoOcorrenciaCentroSaudeDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.sgoOcorrenciaCentroSaude.model.SgoOcorrenciaCentroSaude;
import cv.pn.processmanagement.business.sgoOcorrenciaCentroSaude.repository.SgoOcorrenciaCentroSaudeRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SgoOcorrenciaCentroSaudeService implements ISgoOcorrenciaCentroSaudeService{

    private final SgoOcorrenciaCentroSaudeRepository sgoOcorrenciaCentroSaudeRepository;

    public SgoOcorrenciaCentroSaudeService(SgoOcorrenciaCentroSaudeRepository sgoOcorrenciaCentroSaudeRepository) {
        this.sgoOcorrenciaCentroSaudeRepository = sgoOcorrenciaCentroSaudeRepository;
    }

    @Override
    public APIResponse createCentroSaude(SgoOcorrenciaCentroSaudeDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            SgoOcorrenciaCentroSaude sgoOcorrenciaCentroSaude = new SgoOcorrenciaCentroSaude();
            BeanUtils.copyProperties(dto, sgoOcorrenciaCentroSaude);
            sgoOcorrenciaCentroSaude.setUserCreate("ADMIN");

            sgoOcorrenciaCentroSaudeRepository.save(sgoOcorrenciaCentroSaude);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Ocorrencia Centro Saude criada com sucesso")
                    .setDetails(Collections.singletonList(sgoOcorrenciaCentroSaude))
                    .builder();



        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Ocorrencia Centro Saude")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
