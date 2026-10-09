package cv.pn.processmanagement.business.sgoIntervenente.model.service;


import cv.pn.processmanagement.business.sgoIntervenente.model.SgoPecaIntervenente;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.sgoIntervenente.model.repository.SgoPecaIntervenenteRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SgoPecaIntervenenteService implements ISgoPecaIntervenenteService{

    private final SgoPecaIntervenenteRepository sgoPecaIntervenenteRepository;

    public SgoPecaIntervenenteService(SgoPecaIntervenenteRepository sgoPecaIntervenenteRepository) {
        this.sgoPecaIntervenenteRepository = sgoPecaIntervenenteRepository;
    }

    @Override
    public APIResponse createSgoPecaInter(SgoPecaIntervenente dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            System.out.println("DTO RECEBIDO:" + dto.getOracleId());
            SgoPecaIntervenente sgoPecaIntervenente = new  SgoPecaIntervenente();
            BeanUtils.copyProperties(dto, sgoPecaIntervenente);

            sgoPecaIntervenenteRepository.save(sgoPecaIntervenente);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("sgoPeça criada com sucesso")
                    .setDetails(Collections.singletonList(sgoPecaIntervenente))
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
