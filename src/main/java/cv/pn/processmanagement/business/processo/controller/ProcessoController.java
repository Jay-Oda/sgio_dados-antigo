package cv.pn.processmanagement.business.processo.controller;


import cv.pn.processmanagement.business.processo.dto.ProcessoDto;
import cv.pn.processmanagement.business.processo.services.IProcessoService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/processos")
public class ProcessoController {

    private final IProcessoService iProcessoService;

    public ProcessoController(IProcessoService iProcessoService) {
        this.iProcessoService = iProcessoService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody ProcessoDto dto) {
        return ResponseEntity.ok(iProcessoService.createProcesso(dto));
    }
}
