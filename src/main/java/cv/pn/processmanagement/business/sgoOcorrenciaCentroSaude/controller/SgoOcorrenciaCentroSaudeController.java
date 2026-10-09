package cv.pn.processmanagement.business.sgoOcorrenciaCentroSaude.controller;


import cv.pn.processmanagement.business.sgoOcorrenciaAnexos.dto.SgoOcorrenciaAnexosDto;
import cv.pn.processmanagement.business.sgoOcorrenciaCentroSaude.dto.SgoOcorrenciaCentroSaudeDto;
import cv.pn.processmanagement.business.sgoOcorrenciaCentroSaude.services.ISgoOcorrenciaCentroSaudeService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sigoOcorrenciaCentroSaude")
public class SgoOcorrenciaCentroSaudeController {

    private final ISgoOcorrenciaCentroSaudeService iSgoOcorrenciaCentroSaudeService;

    public SgoOcorrenciaCentroSaudeController(ISgoOcorrenciaCentroSaudeService iSgoOcorrenciaCentroSaudeService) {
        this.iSgoOcorrenciaCentroSaudeService = iSgoOcorrenciaCentroSaudeService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoOcorrenciaCentroSaudeDto dto) {
        return ResponseEntity.ok(iSgoOcorrenciaCentroSaudeService.createCentroSaude(dto));
    }
}
