package cv.pn.processmanagement.business.crimeOcorrencia.services;


import cv.pn.processmanagement.business.crimeOcorrencia.dto.TipoCrimeTipoOcorrenciaDto;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.crimeOcorrencia.repository.TipoCrimeTipoOcorrenciaRepository;
import cv.pn.processmanagement.business.crimeOcorrencia.model.TipoCrimeTipoOcorrencia;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class TipoCrimeTipoOcorrenciaService implements ITipoCrimeTipoOcorrenciaService {

    private final TipoCrimeTipoOcorrenciaRepository tipoCrimeTipoOcorrenciaRepository;

    public TipoCrimeTipoOcorrenciaService(TipoCrimeTipoOcorrenciaRepository tipoCrimeTipoOcorrenciaRepository) {
        this.tipoCrimeTipoOcorrenciaRepository = tipoCrimeTipoOcorrenciaRepository;
    }

    @Override
    public APIResponse createTipoCrime(TipoCrimeTipoOcorrenciaDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }

        try {
            System.out.println("DTO RECEBIDO:");
            TipoCrimeTipoOcorrencia entity = new TipoCrimeTipoOcorrencia();
            //BeanUtils.copyProperties(dto, entity);
            entity.setId(dto.getId());  // ID do Oracle
            entity.setIdTipoCrime(dto.getIdTipoCrime());
            entity.setIdTpOcorrencia(dto.getIdTpOcorrencia());
           // entity.setUserCreate("ADMIN");
            entity.setUserCreate("MIGRACAO_ORACLE");
            entity.setDateCreate(LocalDateTime.now());
            entity.setActive(true);


            System.out.println("DTO idTipoCrime = " + dto.getIdTipoCrime());
            System.out.println("DTO idTpOcorrencia = " + dto.getIdTpOcorrencia());
            tipoCrimeTipoOcorrenciaRepository.save(entity);

            System.out.println("ENTITY APÓS SAVE:");
            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("TipoCrimeTipoOcorrencia criada com sucesso")
                    .setDetails(Collections.singletonList(entity))
                    .builder();


        } catch (Exception e) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar TipoCrimeTipoOcorrencia")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }
}
