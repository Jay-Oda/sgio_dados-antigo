package cv.pn.processmanagement.business.sgoOcorrenciaParentesco.controller;


import cv.pn.processmanagement.business.sgoOcorrenciaParentesco.dto.SgoOcorrenciaParentescoDto;
import cv.pn.processmanagement.business.sgoOcorrenciaParentesco.services.ISgoOcorrenciaParentescoService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sigoOcorrenciaParentesco")
public class SgoOcorrenciaParentescoController {

    private final ISgoOcorrenciaParentescoService iSgoOcorrenciaParentescoService;

    public SgoOcorrenciaParentescoController(ISgoOcorrenciaParentescoService iSgoOcorrenciaParentescoService) {
        this.iSgoOcorrenciaParentescoService = iSgoOcorrenciaParentescoService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoOcorrenciaParentescoDto dto) {
        return ResponseEntity.ok(iSgoOcorrenciaParentescoService.createParentesco(dto));
    }
}
