package cv.pn.processmanagement.business.comando.controller;


import cv.pn.processmanagement.business.comando.dto.ComandoDto;
import cv.pn.processmanagement.business.comando.services.IComandoService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comandos")
public class ComandoController {

    private final IComandoService iComandoService;

    public ComandoController(IComandoService iComandoService) {
        this.iComandoService = iComandoService;
    }


    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody ComandoDto dto) {
        return ResponseEntity.ok(iComandoService.createComando(dto));
    }
}
