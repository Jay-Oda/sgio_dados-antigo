package cv.pn.processmanagement.business.sigoOcorrencia.controller;


import cv.pn.processmanagement.business.sigoOcorrencia.dto.SgoTpOcorrenciaDTO;
import cv.pn.processmanagement.business.sigoOcorrencia.services.ISgoTpOcorrenciaService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sigoTpOcorrencias")
public class SgoTpOcorrenciaController {

    private final ISgoTpOcorrenciaService iSgoTpOcorrenciaService;

    public SgoTpOcorrenciaController(ISgoTpOcorrenciaService iSgoTpOcorrenciaService) {
        this.iSgoTpOcorrenciaService = iSgoTpOcorrenciaService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoTpOcorrenciaDTO dto) {
        return ResponseEntity.ok(iSgoTpOcorrenciaService.createSgo(dto));
    }
}
