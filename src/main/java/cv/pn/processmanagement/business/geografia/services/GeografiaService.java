package cv.pn.processmanagement.business.geografia.services;


import cv.pn.processmanagement.business.geografia.dto.GeografiaDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.geografia.model.Geografica;
import cv.pn.processmanagement.business.geografia.repository.GeografiaRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GeografiaService implements IGeografiaService {

    private final GeografiaRepository geografiaRepository;

    public GeografiaService(GeografiaRepository geografiaRepository) {
        this.geografiaRepository = geografiaRepository;
    }

    @Override
    public APIResponse createGeografia(GeografiaDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            Geografica geografica = new Geografica();
            BeanUtils.copyProperties(dto, geografica);
            geografica.setUserCreate("ADMIN");

            geografiaRepository.save(geografica);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("geografica criada com sucesso")
                    .setDetails(Collections.singletonList(geografica))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar geografia")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
