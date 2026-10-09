package cv.pn.processmanagement.business.comarca.services;

import cv.pn.processmanagement.business.comarca.dto.ComarcaDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.comarca.model.Comarca;
import cv.pn.processmanagement.business.comarca.repository.ComarcaRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ComarcaService implements IComarcaService {

    private final ComarcaRepository comarcaRepository;

    public ComarcaService(ComarcaRepository comarcaRepository) {
        this.comarcaRepository = comarcaRepository;
    }

    @Override
    public APIResponse createComarca(ComarcaDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try{

            Comarca comarca = new Comarca();
            BeanUtils.copyProperties(dto, comarca);
            comarca.setUserCreate("ADMIN");
            comarcaRepository.save(comarca);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Comarca criada com sucesso")
                    .setDetails(Collections.singletonList(comarca))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Esquadra")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }

    }
}
