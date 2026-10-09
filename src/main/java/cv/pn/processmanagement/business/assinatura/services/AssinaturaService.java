package cv.pn.processmanagement.business.assinatura.services;


import cv.pn.processmanagement.business.assinatura.dto.AssinaturaDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.assinatura.model.Assinatura;
import cv.pn.processmanagement.business.assinatura.repository.AssinaturaRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AssinaturaService implements IAssinaturaService {

    private final AssinaturaRepository assinaturaRepository;

    public AssinaturaService(AssinaturaRepository assinaturaRepository) {
        this.assinaturaRepository = assinaturaRepository;
    }

    @Override
    public APIResponse createAssinatura(AssinaturaDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            Assinatura assinatura = new Assinatura();
            BeanUtils.copyProperties(dto, assinatura);
            assinatura.setUserCreate("ADMIN");
            assinaturaRepository.save(assinatura);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Assinatura criada com sucesso")
                    .setDetails(Collections.singletonList(assinatura))
                    .builder();

        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Assinatura")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }

    }
}
