package cv.pn.processmanagement.business.sgoOcorrenciaServicoApoio.controller;


import cv.pn.processmanagement.business.sgoOcorrenciaRisco.dto.SgoOcorrenciaRiscoDto;
import cv.pn.processmanagement.business.sgoOcorrenciaServicoApoio.dto.SgoOcorrenciaServicoApoioDto;
import cv.pn.processmanagement.business.sgoOcorrenciaServicoApoio.services.ISgoOcorrenciaServicoApoioService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sigoOcorrenciaServicoApoio")
public class SgoOcorrenciaServicoApoioController {

    private final ISgoOcorrenciaServicoApoioService iSgoOcorrenciaServicoApoioService;

    public SgoOcorrenciaServicoApoioController(ISgoOcorrenciaServicoApoioService iSgoOcorrenciaServicoApoioService) {
        this.iSgoOcorrenciaServicoApoioService = iSgoOcorrenciaServicoApoioService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoOcorrenciaServicoApoioDto dto) {
        return ResponseEntity.ok(iSgoOcorrenciaServicoApoioService.createServicoApoio(dto));
    }
}
