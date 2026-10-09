package cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.sgoHistoricoAutorVbg.service;


import cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.sgoHistoricoAutorVbg.dto.SgoHistoricoAutorVbgDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.sgoHistoricoAutorVbg.model.SgoHistoricoAutorVbg;
import cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.sgoHistoricoAutorVbg.repository.SgoHistoricoAutorVbgRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SgoHistoricoAutorVbgService implements ISgoHistoricoAutorVbgService{

    private final SgoHistoricoAutorVbgRepository sgoHistoricoAutorVbgRepository;

    public SgoHistoricoAutorVbgService(SgoHistoricoAutorVbgRepository sgoHistoricoAutorVbgRepository) {
        this.sgoHistoricoAutorVbgRepository = sgoHistoricoAutorVbgRepository;
    }

    @Override
    public APIResponse createAutorVbg(SgoHistoricoAutorVbgDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            System.out.println("DTO RECEBIDO:");
            SgoHistoricoAutorVbg sgoHistoricoAutorVbg = new SgoHistoricoAutorVbg();
            BeanUtils.copyProperties(dto, sgoHistoricoAutorVbg);

            sgoHistoricoAutorVbgRepository.save(sgoHistoricoAutorVbg);

            System.out.println("ENTITY APÓS SAVE:");

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("SigoAutor criada com sucesso")
                    .setDetails(Collections.singletonList(sgoHistoricoAutorVbg))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar SigoAutor")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
