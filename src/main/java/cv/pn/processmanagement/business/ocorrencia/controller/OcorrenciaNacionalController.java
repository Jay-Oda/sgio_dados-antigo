package cv.pn.processmanagement.business.ocorrencia.controller;

import cv.pn.processmanagement.business.ocorrencia.services.IOcorrenciaNacionalService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sigo/v1")
public class OcorrenciaNacionalController {

    private final IOcorrenciaNacionalService ocorrenciaNacionalService;

    public OcorrenciaNacionalController(IOcorrenciaNacionalService ocorrenciaNacionalService) {
        this.ocorrenciaNacionalService = ocorrenciaNacionalService;
    }

    @GetMapping("/Ocorrencia")
    public ResponseEntity<APIResponse> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(required = false) String numOcorrencia,
            @RequestParam(required = false) String tpAuto,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim,
            @RequestParam(required = false) String natureza,
            @RequestParam(required = false) String tipologia,
            @RequestParam(required = false) String enquadramento,
            @RequestParam(required = false) Long comando,
            @RequestParam(required = false) Long unidade) {

        return ResponseEntity.ok(ocorrenciaNacionalService.listarOcorrencias(
                page, size, numOcorrencia, tpAuto, estado, dataInicio, dataFim,
                natureza, tipologia, enquadramento, comando, unidade));
    }

    @GetMapping("/Ocorrencia/opcoes")
    public ResponseEntity<?> opcoes() {
        return ResponseEntity.ok(ocorrenciaNacionalService.obterOpcoes());
    }
}