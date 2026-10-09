package cv.pn.processmanagement.business.esquadra.controller;


import cv.pn.processmanagement.business.esquadra.dto.EsquadraDto;
import cv.pn.processmanagement.business.esquadra.services.IEsquadraService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/esquadra")
public class EsquadraController {

    private final IEsquadraService iEsquadraService;

    public EsquadraController(IEsquadraService iEsquadraService) {
        this.iEsquadraService = iEsquadraService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody EsquadraDto dto) {
        return ResponseEntity.ok(iEsquadraService.createEsquadra(dto));
    }
}
