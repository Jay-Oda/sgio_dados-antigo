package cv.pn.processmanagement.sgoPeca.service;

import cv.pn.processmanagement.sgoPeca.SgoPeca;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.sgoPeca.dto.SgoPecaDto;
import cv.pn.processmanagement.sgoPeca.repository.SgoPecaRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SgoPecaService implements ISgoPecaService {

    private final SgoPecaRepository sgoPecaRepository;

    public SgoPecaService(SgoPecaRepository sgoPecaRepository) {
        this.sgoPecaRepository = sgoPecaRepository;
    }

    @Override
    public APIResponse createSgoPeca(SgoPecaDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {
            System.out.println("DTO RECEBIDO:" + dto.getOracleId());
            SgoPeca sgoPeca = new SgoPeca();
            BeanUtils.copyProperties(dto, sgoPeca);

            //sgoPeca.setUserCreate("ADMIN");
           // sgoPeca.setUserCreate("MIGRACAO");

            sgoPecaRepository.save(sgoPeca);

            System.out.println("ENTITY APÓS SAVE:");

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("sgoPeça criada com sucesso")
                    .setDetails(Collections.singletonList(sgoPeca))
                    .builder();


        } catch (Exception e) {

            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar sgoPeça")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }

    }
}
