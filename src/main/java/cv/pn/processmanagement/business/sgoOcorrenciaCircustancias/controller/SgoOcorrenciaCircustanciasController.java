package cv.pn.processmanagement.business.sgoOcorrenciaCircustancias.controller;


import cv.pn.processmanagement.business.sgoOcorrenciaCentroSaude.dto.SgoOcorrenciaCentroSaudeDto;
import cv.pn.processmanagement.business.sgoOcorrenciaCircustancias.dto.SgoOcorrenciaCircustanciasDto;
import cv.pn.processmanagement.business.sgoOcorrenciaCircustancias.services.ISgoOcorrenciaCircustanciasService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sigoOcorrenciaCircustancias")
public class SgoOcorrenciaCircustanciasController {

    private final ISgoOcorrenciaCircustanciasService iSgoOcorrenciaCircustanciasService;

    public SgoOcorrenciaCircustanciasController(ISgoOcorrenciaCircustanciasService iSgoOcorrenciaCircustanciasService) {
        this.iSgoOcorrenciaCircustanciasService = iSgoOcorrenciaCircustanciasService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoOcorrenciaCircustanciasDto dto) {
        return ResponseEntity.ok(iSgoOcorrenciaCircustanciasService.createCircustancias(dto));
    }
}
