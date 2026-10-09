package cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.controller;

import cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.dto.Sgo_Ocorrencia_TP_OcorrenciaDto;
import cv.pn.processmanagement.business.sgoOcorrenciaTPOcorrencia.services.ISgo_Ocorrencia_TP_OcorrenciaService;
import cv.pn.processmanagement.business.sigoOcorrencia.dto.SgoTpOcorrenciaDTO;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ocorrencias-tp_ocorrencias")
public class Sgo_Ocorrencia_TP_OcorrenciaController {

    private final ISgo_Ocorrencia_TP_OcorrenciaService iSgoOcorrenciaTpOcorrenciaService;

    public Sgo_Ocorrencia_TP_OcorrenciaController(ISgo_Ocorrencia_TP_OcorrenciaService iSgoOcorrenciaTpOcorrenciaService) {
        this.iSgoOcorrenciaTpOcorrenciaService = iSgoOcorrenciaTpOcorrenciaService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody Sgo_Ocorrencia_TP_OcorrenciaDto dto) {
        return ResponseEntity.ok(iSgoOcorrenciaTpOcorrenciaService.createSgoTP(dto));
    }
}
