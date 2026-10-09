package cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.services;

import cv.pn.processmanagement.business.ocorrencia.repository.SgoOcorrenciaRepository;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.dto.Sgo_Ocorrencia_TP_OcorrenciaDto;
import cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.model.Sgo_Ocorrencia_TP_Ocorrencia;
import cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.repository.Sgo_Ocorrencia_TP_OcorrenciaRepository;
import cv.pn.processmanagement.business.sigoOcorrencia.model.SgoTpOcorrencia;
import cv.pn.processmanagement.business.sigoOcorrencia.repository.SgoTpOcorrenciaRepository;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class Sgo_Ocorrencia_TP_OcorrenciaService implements ISgo_Ocorrencia_TP_OcorrenciaService{

    private final Sgo_Ocorrencia_TP_OcorrenciaRepository sgoOcorrenciaTpOcorrenciaRepository;
    private final SgoTpOcorrenciaRepository sgoTpOcorrenciaRepository;
    private final SgoOcorrenciaRepository ocorrenciaRepository;

    public Sgo_Ocorrencia_TP_OcorrenciaService(Sgo_Ocorrencia_TP_OcorrenciaRepository sgoOcorrenciaTpOcorrenciaRepository, SgoTpOcorrenciaRepository sgoTpOcorrenciaRepository, SgoOcorrenciaRepository ocorrenciaRepository) {
        this.sgoOcorrenciaTpOcorrenciaRepository = sgoOcorrenciaTpOcorrenciaRepository;
        this.sgoTpOcorrenciaRepository = sgoTpOcorrenciaRepository;
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    @Override
    public APIResponse createSgoTP(Sgo_Ocorrencia_TP_OcorrenciaDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {

            System.out.println("DTO RECEBIDO:");

            Sgo_Ocorrencia_TP_Ocorrencia sgo_ocorrencia_tp_ocorrencia = new Sgo_Ocorrencia_TP_Ocorrencia();
           // BeanUtils.copyProperties(dto, sgo_ocorrencia_tp_ocorrencia);
            sgo_ocorrencia_tp_ocorrencia.setUserCreate("ADMIN");
            sgo_ocorrencia_tp_ocorrencia.setSituacao(dto.getSituacao());
            sgo_ocorrencia_tp_ocorrencia.setEspecificidade(dto.getEspecificidade());

            // Ligar Tipo de Ocorrência pelo oracle_id
            if (dto.getIdTpOcorrencia() != null) {
                SgoTpOcorrencia tipo = sgoTpOcorrenciaRepository
                        .findByOracleId(dto.getIdTpOcorrencia())
                        .orElseThrow(() -> new RuntimeException(
                                "Tipo de Ocorrência não encontrado: oracle_id = " + dto.getIdTpOcorrencia()
                        ));

                sgo_ocorrencia_tp_ocorrencia.setTipoOcorrencia(tipo);
            }

            // ligar TIPO pelo oracle_id
            SgoTpOcorrencia tipo = sgoTpOcorrenciaRepository
                    .findByOracleId(dto.getIdTpOcorrencia())
                    .orElseThrow(() -> new RuntimeException(
                            "Tipo de Ocorrência não encontrado: oracle_id = " + dto.getIdTpOcorrencia()
                    ));
                sgo_ocorrencia_tp_ocorrencia.setTipoOcorrencia(tipo);

            sgoOcorrenciaTpOcorrenciaRepository.save(sgo_ocorrencia_tp_ocorrencia);

            System.out.println("ENTITY APÓS SAVE:");


            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Ocorrencia TP criada com sucesso")
                    .setDetails(Collections.singletonList(sgo_ocorrencia_tp_ocorrencia))
                    .builder();


        } catch (Exception e) {

            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar sgoTpOcorrencia")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
