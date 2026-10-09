package cv.pn.processmanagement.business.sigoPessoa.controller;


import cv.pn.processmanagement.business.sigoPessoa.PessoaFiltroRequest;
import cv.pn.processmanagement.business.sigoPessoa.dto.SigoPessoaDto;
import cv.pn.processmanagement.business.sigoPessoa.model.SigoPessoa;
import cv.pn.processmanagement.business.sigoPessoa.repository.SigoPessoaRepository;
import cv.pn.processmanagement.business.sigoPessoa.services.ISigoPessoaService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/sgopessoas")
public class SigoPessoaController {

    private final ISigoPessoaService iSigoPessoaService;
    private final SigoPessoaRepository sigoPessoaRepository;

    public SigoPessoaController(ISigoPessoaService iSigoPessoaService, SigoPessoaRepository sigoPessoaRepository) {
        this.iSigoPessoaService = iSigoPessoaService;
        this.sigoPessoaRepository = sigoPessoaRepository;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody SigoPessoaDto dto) {
        return ResponseEntity.ok(iSigoPessoaService.createSigoPessoa(dto));
    }

    @GetMapping("/oracle-ids")
    public List<Long> listarOracleIds() {
        return sigoPessoaRepository.findAll()
                .stream()
                .map(SigoPessoa::getOracleId)
                .filter(Objects::nonNull)
                .toList();
    }

    @PostMapping("/pessoa/pesquisar-avancado")
    public ResponseEntity<APIResponse> pesquisar(@RequestBody PessoaFiltroRequest req) {
        return ResponseEntity.ok(iSigoPessoaService.pesquisarPessoaComFiltro(req));
    }

}
