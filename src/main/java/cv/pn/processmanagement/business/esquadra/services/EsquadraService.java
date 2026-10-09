package cv.pn.processmanagement.business.esquadra.services;


import cv.pn.processmanagement.business.esquadra.dto.EsquadraDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.esquadra.model.Esquadra;
import cv.pn.processmanagement.business.esquadra.repository.EsquadraRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class EsquadraService implements IEsquadraService {

    private final EsquadraRepository repository;

    public EsquadraService(EsquadraRepository repository) {
        this.repository = repository;
    }

    @Override
    public APIResponse createEsquadra(EsquadraDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {
            Esquadra e = new Esquadra();
            e.setId(null);       // ID do Postgres
// NÃO mexer no oracleId

            BeanUtils.copyProperties(dto, e);
            e.setUserCreate("ADMIN");
            repository.save(e);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Esquadra criada com sucesso")
                    .setDetails(Collections.singletonList(e))
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
