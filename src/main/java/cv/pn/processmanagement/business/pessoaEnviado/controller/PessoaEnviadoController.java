package cv.pn.processmanagement.business.pessoaEnviado.controller;


import cv.pn.processmanagement.business.pessoaEnviado.dto.PessoaEnviadoDto;
import cv.pn.processmanagement.business.pessoaEnviado.services.IPessoaEnviadoService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pessoa-enviado")
public class PessoaEnviadoController {

    private final IPessoaEnviadoService iPessoaEnviadoService;

    public PessoaEnviadoController(IPessoaEnviadoService iPessoaEnviadoService) {
        this.iPessoaEnviadoService = iPessoaEnviadoService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody PessoaEnviadoDto dto) {
        return ResponseEntity.ok(iPessoaEnviadoService.createPessoaEnviado(dto));
    }
}
