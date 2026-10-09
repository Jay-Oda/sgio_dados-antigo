package cv.pn.processmanagement.business.processo.services;


import cv.pn.processmanagement.business.processo.dto.ProcessoDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.processo.model.Processo;
import cv.pn.processmanagement.business.processo.repository.ProcessoRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ProcessoService implements IProcessoService {

    private final ProcessoRepository processoRepository;

    public ProcessoService(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    @Override
    public APIResponse createProcesso(ProcessoDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            Processo processo = new Processo();
            BeanUtils.copyProperties(dto, processo);
            processo.setUserCreate("ADMIN");
            processoRepository.save(processo);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Processo criada com sucesso")
                    .setDetails(Collections.singletonList(processo))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Processo")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
