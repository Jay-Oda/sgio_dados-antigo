// ===========================================================================
// CONTROLADOR COMENTADO A PEDIDO (2026-10-20): fora do Swagger
// de "Sigo dados - antigo". Para reativar, remover o prefixo "// "
// do inicio de cada linha (o bloco comeca na linha seguinte a esta nota).
// ===========================================================================
// package cv.pn.processmanagement.business.agente.controller;


// import cv.pn.processmanagement.business.agente.service.IAgenteService;
// import cv.pn.processmanagement.business.agente.dto.AgenteDto;
// import cv.pn.processmanagement.utilities.APIResponse;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.media.Schema;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/agente/v1")
// public class AgenteController {

//     private final IAgenteService iAgenteService;

//     public AgenteController(IAgenteService iAgenteService) {
//         this.iAgenteService = iAgenteService;
//     }

//     @PostMapping
//     public ResponseEntity<APIResponse> create(@RequestBody AgenteDto dto) {
//         return ResponseEntity.ok(iAgenteService.createAgente(dto));
//     }

//     @PostMapping("/params")
//     public ResponseEntity<APIResponse> createAgenteParams(@RequestParam String numero,
//                                                            @RequestParam String nome){
//         return ResponseEntity.ok(iAgenteService.createAgenteParams(numero, nome));
//     }

//     @GetMapping("/numero/{numero}")
//     public ResponseEntity<APIResponse> buscarPorNumero(
//             @PathVariable String numero) {
//         return ResponseEntity.ok(iAgenteService.buscarPorNumero(numero));
//     }

//     @GetMapping("/nome/{nome}")
//     public ResponseEntity<APIResponse> buscarPorNomeExato(
//             @PathVariable String nome) {
//         return ResponseEntity.ok(iAgenteService.buscarPorNomeExato(nome));
//     }

//     @GetMapping("/detalhe/{id}")
//     public ResponseEntity<APIResponse> buscarPorId(
//             @PathVariable String id) {
//         return ResponseEntity.ok(iAgenteService.buscarPorId(id));
//     }

//     @GetMapping("/autodenuncia/{nome}")
//     public ResponseEntity<APIResponse> listarAutodenuncia(
//             @PathVariable String nome,
//             @RequestParam(defaultValue = "false") boolean todas) {
//         return ResponseEntity.ok(iAgenteService.listarQueixasDoAgente(nome, todas));
//     }

//     @GetMapping("/ocorrencias/{nome}")
//     public ResponseEntity<APIResponse> listarOcorrencias(
//             @PathVariable String nome,
//             @Parameter(description = "Filtro por tipo de auto", schema = @Schema(type = "string",
//                     allowableValues = {"Auto de Denúncia", "Auto de Notícia", "Auto de Detenção", "Auto de Denúncia VBG", "Auto de Identificação",
//                             "AUTO DE APREENSÃO", "AUTO DE APREENSÃO DE ARMA DE FOGO", "AUTO DE DECLARAÇÃO DE CULPA", "AUTO DE EXAME DIRECTO E AVALIAÇÃO",
//                             "AUTO DE EXAME E PERITAGEM", "AUTO DE RECOLHA"}))
//             @RequestParam(required = false) String auto) {
//         return ResponseEntity.ok(iAgenteService.listarOcorrenciasDoAgente(nome, auto));
//     }

//     @GetMapping("/ocorrencias/envolvidos/{nome}")
//     public ResponseEntity<APIResponse> listarOcorrenciasEnvolvimento(
//             @PathVariable String nome,
//             @Parameter(description = "Filtro por tipo de auto", schema = @Schema(type = "string", allowableValues = {"Auto de Denúncia", "Auto de Notícia",
//                     "Auto de Detenção", "Auto de Denúncia VBG", "Auto de Identificação", "AUTO DE APREENSÃO", "AUTO DE APREENSÃO DE ARMA DE FOGO", "AUTO DE DECLARAÇÃO DE CULPA",
//                     "AUTO DE EXAME DIRECTO E AVALIAÇÃO", "AUTO DE EXAME E PERITAGEM", "AUTO DE RECOLHA"}))
//             @RequestParam(required = false) String auto,
//             @Parameter(description = "Filtro por papel do agente na ocorrência", schema = @Schema(type = "string", allowableValues = {"Denunciante", "Ofendido", "Denunciado",
//                     "Testemunha", "Detido", "Detentor", "Agente Atuante", "Participante"}))
//             @RequestParam(required = false) String condicao) {
//         return ResponseEntity.ok(iAgenteService.listarOcorrenciasEnvolvimentoDoAgente(nome, auto, condicao));
//     }

//     @GetMapping("/ocorrencia/contra/{nome}")
//     public ResponseEntity<APIResponse> listarOcorrenciasContra(
//             @PathVariable String nome,
//             @Parameter(description = "Filtro por tipo de auto", schema = @Schema(type = "string", allowableValues = {"Auto de Denúncia", "Auto de Detenção",
//                     "Auto de Denúncia VBG"}))
//             @RequestParam(required = false) String auto) {
//         return ResponseEntity.ok(iAgenteService.listarOcorrenciasContraAgente(nome, auto));
//     }
// }
