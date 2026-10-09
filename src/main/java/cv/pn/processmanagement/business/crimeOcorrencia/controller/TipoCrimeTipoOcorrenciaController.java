package cv.pn.processmanagement.business.crimeOcorrencia.controller;


import cv.pn.processmanagement.business.crimeOcorrencia.dto.TipoCrimeTipoOcorrenciaDto;
import cv.pn.processmanagement.business.crimeOcorrencia.services.ITipoCrimeTipoOcorrenciaService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tipocrime-tipocorrencia")
public class TipoCrimeTipoOcorrenciaController {

    private final ITipoCrimeTipoOcorrenciaService iTipoCrimeTipoOcorrenciaService;

    public TipoCrimeTipoOcorrenciaController(ITipoCrimeTipoOcorrenciaService iTipoCrimeTipoOcorrenciaService) {
        this.iTipoCrimeTipoOcorrenciaService = iTipoCrimeTipoOcorrenciaService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody TipoCrimeTipoOcorrenciaDto dto) {
        return ResponseEntity.ok(iTipoCrimeTipoOcorrenciaService.createTipoCrime(dto));
    }
}
