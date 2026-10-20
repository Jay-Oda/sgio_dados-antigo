// ===========================================================================
// CONTROLADOR COMENTADO A PEDIDO (2026-10-20): fora do Swagger
// de "Sigo dados - antigo". Para reativar, remover o prefixo "// "
// do inicio de cada linha (o bloco comeca na linha seguinte a esta nota).
// ===========================================================================
// package cv.pn.processmanagement.business.sigoOcorrenciaPessoa.controller;



// import cv.pn.processmanagement.business.projection.dto.OcorrenciaResumoDto;
// import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.dto.SgoOcorrenciaPessoaDto;

// import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.model.SgoOcorrenciaPessoa;
// import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.repository.SgoOcorrenciaPessoaRepository;
// import cv.pn.processmanagement.business.sigoOcorrenciaPessoa.services.ISgoOcorrenciaPessoaServices;
// import cv.pn.processmanagement.utilities.APIResponse;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Objects;


// @RestController
// @RequestMapping("/api/sigoOcorrenciaPessoa")
// public class SgoOcorrenciaPessoaController {

//     private final ISgoOcorrenciaPessoaServices iSgoOcorrenciaPessoaServices;
//     private final SgoOcorrenciaPessoaRepository sgoOcorrenciaPessoaRepository;

//     public SgoOcorrenciaPessoaController(ISgoOcorrenciaPessoaServices iSgoOcorrenciaPessoaServices, SgoOcorrenciaPessoaRepository sgoOcorrenciaPessoaRepository) {
//         this.iSgoOcorrenciaPessoaServices = iSgoOcorrenciaPessoaServices;
//         this.sgoOcorrenciaPessoaRepository = sgoOcorrenciaPessoaRepository;
//     }

//     @PostMapping
//     public ResponseEntity<APIResponse> create(@RequestBody SgoOcorrenciaPessoaDto dto) {
//         return ResponseEntity.ok(iSgoOcorrenciaPessoaServices.createOcorrenciaPessoa(dto));
//     }

//     @GetMapping("/oracle-ids")
//     public List<Long> listarOracleIds() {
//         return sgoOcorrenciaPessoaRepository.findAll()
//                 .stream()
//                 .map(SgoOcorrenciaPessoa::getOracleIdPessoa)
//                 .filter(Objects::nonNull)
//                 .distinct()
//                 .sorted()
//                 .toList();
//     }


//    /* @PostMapping("/pesquisar-pessoa")
//     public ResponseEntity<List<SgoOcorrenciaPessoa>> pesquisarPessoa(
//             @Valid @RequestBody SearchPessoaOcorrenciaDto dto) {

//         return ResponseEntity.ok(
//                 iSgoOcorrenciaPessoaServices.pesquisarPessoaEmOcorrencia(dto)
//         );
//     }*/

//     /*@PostMapping("/existe-ocorrencia")
//     pub lic ResponseEntity<ExisteOcorrenciaResponseDto> existeOcorrencia(
//             @RequestBody SearchPessoaOcorrenciaDto dto) {

//         return ResponseEntity.ok(
//                 iSgoOcorrenciaPessoaServices.pessoaTemOcorrencia(dto)
//         );

//     }*/

//     // estava funcionando bem
//   /*  @PostMapping("/verificar-ocorrencia")
//     public ResponseEntity<APIResponse> verificarOcorrencia(
//             @RequestBody VerificarPessoaRequest request
//     ) {
//         APIResponse response =
//                 iSgoOcorrenciaPessoaServices.verificarAssociacaoPorNome(request.getNome());

//         return ResponseEntity.ok(response);
//     }*/






// }
