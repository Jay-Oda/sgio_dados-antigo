package cv.pn.processmanagement.sgoPecaPessoa.controller;


import cv.pn.processmanagement.sgoPeca.dto.SgoPecaDto;
import cv.pn.processmanagement.sgoPecaPessoa.dto.SgoPecaPessoaDto;
import cv.pn.processmanagement.sgoPecaPessoa.service.ISgoPecaPessoaService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sgo-peca-pessoa")
public class SgoPecaPessoaController {

    private final ISgoPecaPessoaService iSgoPecaPessoaService;

    public SgoPecaPessoaController(ISgoPecaPessoaService iSgoPecaPessoaService) {
        this.iSgoPecaPessoaService = iSgoPecaPessoaService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SgoPecaPessoaDto dto) {
        return ResponseEntity.ok(iSgoPecaPessoaService.createSgoPecaPessoa(dto));
    }
}
