package cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.sgoHistoricoAutorVbg.controller;

import cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.dto.SgoHistoricoVitimaVbgDTO;
import cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.sgoHistoricoAutorVbg.dto.SgoHistoricoAutorVbgDto;
import cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.sgoHistoricoAutorVbg.service.ISgoHistoricoAutorVbgService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/historico-autor-vbg")
public class SgoHistoricoAutorVbgController {

    private final ISgoHistoricoAutorVbgService sgoHistoricoAutorVbgService;

    public SgoHistoricoAutorVbgController(ISgoHistoricoAutorVbgService sgoHistoricoAutorVbgService) {
        this.sgoHistoricoAutorVbgService = sgoHistoricoAutorVbgService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoHistoricoAutorVbgDto dto) {
        return ResponseEntity.ok(sgoHistoricoAutorVbgService.createAutorVbg(dto));
    }
}
