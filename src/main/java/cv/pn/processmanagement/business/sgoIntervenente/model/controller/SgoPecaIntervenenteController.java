// ===========================================================================
// CONTROLADOR COMENTADO A PEDIDO (2026-10-20): fora do Swagger
// de "Sigo dados - antigo". Para reativar, remover o prefixo "// "
// do inicio de cada linha (o bloco comeca na linha seguinte a esta nota).
// ===========================================================================
// package cv.pn.processmanagement.business.sgoIntervenente.model.controller;

// import cv.pn.processmanagement.business.sgoIntervenente.model.SgoPecaIntervenente;
// import cv.pn.processmanagement.business.sgoIntervenente.model.service.ISgoPecaIntervenenteService;
// import cv.pn.processmanagement.sgoPeca.dto.SgoPecaDto;
// import cv.pn.processmanagement.utilities.APIResponse;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/api/sgoPecaIntervenente")
// public class SgoPecaIntervenenteController {

//     private final ISgoPecaIntervenenteService iSgoPecaIntervenenteService;

//     public SgoPecaIntervenenteController(ISgoPecaIntervenenteService iSgoPecaIntervenenteService) {
//         this.iSgoPecaIntervenenteService = iSgoPecaIntervenenteService;
//     }

//     @PostMapping
//     public ResponseEntity<APIResponse> create(@RequestBody SgoPecaIntervenente dto) {
//         return ResponseEntity.ok(iSgoPecaIntervenenteService.createSgoPecaInter(dto));
//     }
// }
