package cv.pn.processmanagement.business.sgoOcorrenciaMeios.controller;


import cv.pn.processmanagement.business.sgoOcorrenciaMeios.dto.SgoOcorrenciaMeiosDto;
import cv.pn.processmanagement.business.sgoOcorrenciaMeios.services.ISgoOcorrenciaMeiosService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sigoOcorrenciaMeios")
public class SgoOcorrenciaMeiosController {

    private final ISgoOcorrenciaMeiosService iSgoOcorrenciaMeiosService;

    public SgoOcorrenciaMeiosController(ISgoOcorrenciaMeiosService iSgoOcorrenciaMeiosService) {
        this.iSgoOcorrenciaMeiosService = iSgoOcorrenciaMeiosService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoOcorrenciaMeiosDto dto) {
        return ResponseEntity.ok(iSgoOcorrenciaMeiosService.createEstado(dto));
    }
}
