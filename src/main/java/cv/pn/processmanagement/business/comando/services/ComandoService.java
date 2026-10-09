package cv.pn.processmanagement.business.comando.services;


import cv.pn.processmanagement.business.comando.dto.ComandoDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.comando.model.Comando;
import cv.pn.processmanagement.business.comando.repository.ComandoRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ComandoService implements IComandoService {

    private final ComandoRepository comarcaRepository;


    public ComandoService(ComandoRepository comarcaRepository) {
        this.comarcaRepository = comarcaRepository;
    }

    @Override
    public APIResponse createComando(ComandoDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            Comando comando = new Comando();
            BeanUtils.copyProperties(dto,comando);
            comando.setUserCreate("ADMIN");

            comarcaRepository.save(comando);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Comando criada com sucesso")
                    .setDetails(Collections.singletonList(comando))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar comando")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
