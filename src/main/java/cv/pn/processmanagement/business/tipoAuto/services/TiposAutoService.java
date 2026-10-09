package cv.pn.processmanagement.business.tipoAuto.services;


import cv.pn.processmanagement.business.tipoAuto.dto.TiposAutoDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.tipoAuto.repository.TiposAutoRepository;
import cv.pn.processmanagement.business.tipoAuto.model.TiposAuto;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class TiposAutoService implements ITiposAutoService {

    private final TiposAutoRepository tiposAutoRepository;

    public TiposAutoService(TiposAutoRepository tiposAutoRepository) {
        this.tiposAutoRepository = tiposAutoRepository;
    }

    @Override
    public APIResponse createTiposAuto(TiposAutoDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            TiposAuto tiposAuto = new TiposAuto();
            BeanUtils.copyProperties(dto, tiposAuto);
            tiposAuto.setUserCreate("ADMIN");
            tiposAutoRepository.save(tiposAuto);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("TipoAuto criada com sucesso")
                    .setDetails(Collections.singletonList(tiposAuto))
                    .builder();

        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar TipoAuto")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
