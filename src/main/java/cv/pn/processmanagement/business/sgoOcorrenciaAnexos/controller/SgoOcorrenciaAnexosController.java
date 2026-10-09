package cv.pn.processmanagement.business.sgoOcorrenciaAnexos.controller;


import cv.pn.processmanagement.business.sgoOcorrenciaAnexo.dto.SgoOcorrenciaAnexoDto;
import cv.pn.processmanagement.business.sgoOcorrenciaAnexos.dto.SgoOcorrenciaAnexosDto;
import cv.pn.processmanagement.business.sgoOcorrenciaAnexos.services.ISgoOcorrenciaAnexosService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sigoOcorrenciaAnexos")
public class SgoOcorrenciaAnexosController {

    private final ISgoOcorrenciaAnexosService iSgoOcorrenciaAnexosService;

    public SgoOcorrenciaAnexosController(ISgoOcorrenciaAnexosService iSgoOcorrenciaAnexosService) {
        this.iSgoOcorrenciaAnexosService = iSgoOcorrenciaAnexosService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoOcorrenciaAnexosDto dto) {
        return ResponseEntity.ok(iSgoOcorrenciaAnexosService.createAnexos(dto));
    }
}
