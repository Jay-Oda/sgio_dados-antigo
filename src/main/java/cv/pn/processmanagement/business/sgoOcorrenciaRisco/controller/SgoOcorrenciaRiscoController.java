package cv.pn.processmanagement.business.sgoOcorrenciaRisco.controller;


import cv.pn.processmanagement.business.sgoOcorrenciaMeios.dto.SgoOcorrenciaMeiosDto;
import cv.pn.processmanagement.business.sgoOcorrenciaRisco.dto.SgoOcorrenciaRiscoDto;
import cv.pn.processmanagement.business.sgoOcorrenciaRisco.services.ISgoOcorrenciaRiscoService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sigoOcorrenciaRisco")
public class SgoOcorrenciaRiscoController {

    private final ISgoOcorrenciaRiscoService iSgoOcorrenciaRiscoService;

    public SgoOcorrenciaRiscoController(ISgoOcorrenciaRiscoService iSgoOcorrenciaRiscoService) {
        this.iSgoOcorrenciaRiscoService = iSgoOcorrenciaRiscoService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoOcorrenciaRiscoDto dto) {
        return ResponseEntity.ok(iSgoOcorrenciaRiscoService.createRisco(dto));
    }
}
