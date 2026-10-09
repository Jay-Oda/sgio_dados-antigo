package cv.pn.processmanagement.business.ocorrencia.services;

import cv.pn.processmanagement.business.esquadra.model.Esquadra;
import cv.pn.processmanagement.utilities.DtoValidator;
import cv.pn.processmanagement.business.esquadra.repository.EsquadraRepository;
import cv.pn.processmanagement.business.projection.OcorrenciaMotivoProjection;
import cv.pn.processmanagement.business.sigoOcorrencia.model.SgoTpOcorrencia;
import cv.pn.processmanagement.business.sigoOcorrencia.repository.SgoTpOcorrenciaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import cv.pn.processmanagement.business.ocorrencia.dto.SgoOcorrenciaDto;
import cv.pn.processmanagement.business.ocorrencia.model.SgoOcorrencia;
import cv.pn.processmanagement.business.ocorrencia.repository.SgoOcorrenciaRepository;
import cv.pn.processmanagement.utilities.APIResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
public class SgoOcorrenciaService implements ISgoOcorrenciaService{

    private final SgoOcorrenciaRepository sgoOcorrenciaRepository;

    private final EsquadraRepository esquadraRepository;

    private final SgoTpOcorrenciaRepository sgoTpOcorrenciaRepository;

    private final JdbcTemplate jdbcTemplate;

    public SgoOcorrenciaService(SgoOcorrenciaRepository sgoOcorrenciaRepository, EsquadraRepository esquadraRepository, SgoTpOcorrenciaRepository sgoTpOcorrenciaRepository, JdbcTemplate jdbcTemplate) {
        this.sgoOcorrenciaRepository = sgoOcorrenciaRepository;
        this.esquadraRepository = esquadraRepository;
        this.sgoTpOcorrenciaRepository = sgoTpOcorrenciaRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public APIResponse createOcorrencia(SgoOcorrenciaDto dto) {

        if (DtoValidator.isEmpty(dto)) {
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Dados em falta: corpo vazio ou sem campos preenchidos")
                    .setDetails(Collections.emptyList())
                    .builder();
        }
        try {


            System.out.println("DTO RECEBIDO:");
           // System.out.println(new Gson().toJson(dto));

            SgoOcorrencia sgoOcorrencia = new SgoOcorrencia();

            sgoOcorrencia.setId(null);
            BeanUtils.copyProperties(dto, sgoOcorrencia);

            //System.out.println(new Gson().toJson(sgoOcorrencia));
            sgoOcorrencia.setUserCreate("ADMIN");

            // 🔗 LIGAR ESQUADRA PELO ORACLE_ID
           if (dto.getIdEsquadra() != null) {
                Esquadra esquadra = esquadraRepository
                        .findByOracleId(dto.getIdEsquadra())
                        .orElseThrow(() -> new RuntimeException(
                                "Esquadra não encontrada para oracle_id = " + dto.getIdEsquadra()
                        ));

               sgoOcorrencia.setEsquadra(esquadra);


            }


            // Guardar apenas o ID do tipo de ocorrência (oracle)
           /* if (dto.getIdTpOcorrencia() != null) {
                sgoOcorrencia.setIdTpOcorrencia(dto.getIdTpOcorrencia());
            }*/

            // LIGAR TIPO OCORRENCIA PELO ORACLE_ID
            if (dto.getIdTpOcorrencia() != null) {
                SgoTpOcorrencia tp = sgoTpOcorrenciaRepository
                        .findByOracleId(dto.getIdTpOcorrencia())
                        .orElseThrow(() -> new RuntimeException(
                                "Tipo Ocorrência não encontrado para oracle_id = " + dto.getIdTpOcorrencia()
                        ));

                sgoOcorrencia.setTipoOcorrencia(tp);
            }

              sgoOcorrenciaRepository.save(sgoOcorrencia);



            System.out.println("ENTITY APÓS SAVE:");
            //System.out.println(new Gson().toJson(salvo));


            return new APIResponse.buildAPIResponse()
                    .setStatus(true)
                    .setStatusText("Ocorrencia  criada com sucesso")
                    .setDetails(Collections.singletonList(sgoOcorrencia))
                    .builder();


        } catch (Exception e) {

            e.printStackTrace();
            return new APIResponse.buildAPIResponse()
                    .setStatus(false)
                    .setStatusText("Erro ao salvar Ocorrencia ")
                    .setDetails(Collections.singletonList(e.getMessage()))
                    .builder();
        }
    }

    @Override
    public Map<String, Object> consultarMotivo(String num) {


        OcorrenciaMotivoProjection o =
                sgoOcorrenciaRepository.buscarMotivo(num);

        Map<String, Object> resp = new HashMap<>();

        if (o == null) {
            resp.put("status", false);
            resp.put("mensagem", "Ocorrência não encontrada.");
            return resp;
        }

        resp.put("status", true);
        resp.put("numOcorrencia", o.getNumOcorrencia());
        resp.put("motivo", o.getMotivo());

        return resp;
    }


}
