package cv.pn.processmanagement.business.intervinientes.services;


import cv.pn.processmanagement.business.intervinientes.dto.IntervenientesDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.intervinientes.model.Intervenientes;
import cv.pn.processmanagement.business.intervinientes.repository.IntervenientesRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class IntervenientesService implements IIntervenientesService {

    private final IntervenientesRepository intervenientesRepository;

    public IntervenientesService(IntervenientesRepository intervenientesRepository) {
        this.intervenientesRepository = intervenientesRepository;
    }

    @Override
    public APIResponse createIntervinientes(IntervenientesDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {
            Intervenientes entity = new Intervenientes();
            BeanUtils.copyProperties(dto, entity);
            entity.setUserCreate("ADMIN");
            intervenientesRepository.save(entity);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Intervinientes criada com sucesso")
                    .setDetails(Collections.singletonList(entity))
                    .builder();

        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Intervinientes")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
