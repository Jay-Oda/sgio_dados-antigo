package cv.pn.processmanagement.business.sgoOcorrenciaEntrada.controller;


import cv.pn.processmanagement.business.sgoOcorrenciaEntrada.dto.SgoOcorrenciaEntradaDto;
import cv.pn.processmanagement.business.sgoOcorrenciaEntrada.services.ISgoOcorrenciaEntradaService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sigoTpOcorrenciasEntrada")
public class SgoOcorrenciaEntradaController {

    private final ISgoOcorrenciaEntradaService iSgoOcorrenciaEntradaService;

    public SgoOcorrenciaEntradaController(ISgoOcorrenciaEntradaService iSgoOcorrenciaEntradaService) {
        this.iSgoOcorrenciaEntradaService = iSgoOcorrenciaEntradaService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoOcorrenciaEntradaDto dto) {
        return ResponseEntity.ok(iSgoOcorrenciaEntradaService.createEntrada(dto));
    }
}
