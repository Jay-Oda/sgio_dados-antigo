package cv.pn.processmanagement.business.sgoOcorrenciaAnexo.controller;


import cv.pn.processmanagement.business.sgoOcorrenciaAnexo.dto.SgoOcorrenciaAnexoDto;
import cv.pn.processmanagement.business.sgoOcorrenciaAnexo.services.ISgoOcorrenciaAnexoServices;
import cv.pn.processmanagement.business.sgoOcorrenciaEntrada.dto.SgoOcorrenciaEntradaDto;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sigoOcorrenciaAnexo")
public class SgoOcorrenciaAnexoController {

    private final ISgoOcorrenciaAnexoServices iSgoOcorrenciaAnexoServices;

    public SgoOcorrenciaAnexoController(ISgoOcorrenciaAnexoServices iSgoOcorrenciaAnexoServices) {
        this.iSgoOcorrenciaAnexoServices = iSgoOcorrenciaAnexoServices;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoOcorrenciaAnexoDto dto) {
        return ResponseEntity.ok(iSgoOcorrenciaAnexoServices.createAnexo(dto));
    }
}
