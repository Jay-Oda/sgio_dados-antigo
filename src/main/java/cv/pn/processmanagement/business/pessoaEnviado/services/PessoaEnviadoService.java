package cv.pn.processmanagement.business.pessoaEnviado.services;


import cv.pn.processmanagement.business.pessoaEnviado.model.PessoaEnviado;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.pessoaEnviado.dto.PessoaEnviadoDto;
import cv.pn.processmanagement.business.pessoaEnviado.repository.PessoaEnviadoRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PessoaEnviadoService implements IPessoaEnviadoService {

    private final PessoaEnviadoRepository pessoaEnviadoRepository;

    public PessoaEnviadoService(PessoaEnviadoRepository pessoaEnviadoRepository) {
        this.pessoaEnviadoRepository = pessoaEnviadoRepository;
    }


    @Override
    public APIResponse createPessoaEnviado(PessoaEnviadoDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            PessoaEnviado pessoaEnviado = new PessoaEnviado();
            BeanUtils.copyProperties(dto, pessoaEnviado);
            pessoaEnviado.setUserCreate("ADMIN");
            pessoaEnviadoRepository.save(pessoaEnviado);

            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("PessoaEnviado criada com sucesso")
                    .setDetails(Collections.singletonList(pessoaEnviado))
                    .builder();



        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("PessoaEnviado ao salvar Esquadra")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
