package cv.pn.processmanagement.business.ocorrencia.controller;


import cv.pn.processmanagement.business.ocorrencia.dto.SgoOcorrenciaDto;
import cv.pn.processmanagement.business.ocorrencia.services.ISgoOcorrenciaService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/SgoOcorrencia")
public class SgoOcorrenciaController {

    private final ISgoOcorrenciaService iSgoOcorrenciaService;


    public SgoOcorrenciaController(ISgoOcorrenciaService iSgoOcorrenciaService) {
        this.iSgoOcorrenciaService = iSgoOcorrenciaService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoOcorrenciaDto dto) {

        System.out.println("CHEGOU POST /api/SgoOcorrencia");

        return ResponseEntity.ok(iSgoOcorrenciaService.createOcorrencia(dto));
    }

    @GetMapping("/motivo")
    public ResponseEntity<?> motivo(
            @RequestParam  String num) {

        return ResponseEntity.ok(
                iSgoOcorrenciaService.consultarMotivo(num)
        );
    }


}
