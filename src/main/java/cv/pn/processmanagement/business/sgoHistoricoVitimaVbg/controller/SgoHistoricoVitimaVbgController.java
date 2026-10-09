package cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.controller;

import cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.dto.SgoHistoricoVitimaVbgDTO;
import cv.pn.processmanagement.business.sgoHistoricoVitimaVbg.service.ISgoHistoricoVitimaVbgService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/historico-vitima-vbg")
public class SgoHistoricoVitimaVbgController {

    private final ISgoHistoricoVitimaVbgService iSgoHistoricoVitimaVbgService;

    public SgoHistoricoVitimaVbgController(ISgoHistoricoVitimaVbgService iSgoHistoricoVitimaVbgService) {
        this.iSgoHistoricoVitimaVbgService = iSgoHistoricoVitimaVbgService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoHistoricoVitimaVbgDTO dto) {
        return ResponseEntity.ok(iSgoHistoricoVitimaVbgService.createVitimaVbg(dto));
    }
}
