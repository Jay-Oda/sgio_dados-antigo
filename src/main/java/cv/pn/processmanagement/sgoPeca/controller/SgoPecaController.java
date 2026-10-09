package cv.pn.processmanagement.sgoPeca.controller;

import cv.pn.processmanagement.business.sigoOcorrencia.dto.SgoTpOcorrenciaDTO;
import cv.pn.processmanagement.sgoPeca.dto.SgoPecaDto;
import cv.pn.processmanagement.sgoPeca.service.ISgoPecaService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pecas")
public class SgoPecaController {

    private final ISgoPecaService iSgoPecaService;

    public SgoPecaController(ISgoPecaService iSgoPecaService) {
        this.iSgoPecaService = iSgoPecaService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoPecaDto dto) {
        return ResponseEntity.ok(iSgoPecaService.createSgoPeca(dto));
    }
}
