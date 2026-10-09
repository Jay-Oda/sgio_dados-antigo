package cv.pn.processmanagement.sgoPecaPessoa.service;

import cv.pn.processmanagement.sgoPecaPessoa.dto.SgoPecaPessoaDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.sgoPecaPessoa.model.SgoPecaPessoa;
import cv.pn.processmanagement.sgoPecaPessoa.repository.SgoPecaPessoaRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SgoPecaPessoaService implements ISgoPecaPessoaService {

    private final SgoPecaPessoaRepository sgoPecaPessoaRepository;

    public SgoPecaPessoaService(SgoPecaPessoaRepository sgoPecaPessoaRepository) {
        this.sgoPecaPessoaRepository = sgoPecaPessoaRepository;
    }

    @Override
    public APIResponse createSgoPecaPessoa(SgoPecaPessoaDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            System.out.println("DTO RECEBIDO:" + dto.getOracleId());
            SgoPecaPessoa sgoPecaPessoa = new SgoPecaPessoa();
            BeanUtils.copyProperties(dto, sgoPecaPessoa);

            sgoPecaPessoaRepository.save(sgoPecaPessoa);

            System.out.println("ENTITY APÓS SAVE:");

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("sgoPeçaPessoa criada com sucesso")
                    .setDetails(Collections.singletonList(sgoPecaPessoa))
                    .builder();

        } catch (Exception e) {

            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar sgoPeçaPessoa")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
