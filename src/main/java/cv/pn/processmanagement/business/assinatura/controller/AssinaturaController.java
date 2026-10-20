// ===========================================================================
// CONTROLADOR COMENTADO A PEDIDO (2026-10-20): fora do Swagger
// de "Sigo dados - antigo". Para reativar, remover o prefixo "// "
// do inicio de cada linha (o bloco comeca na linha seguinte a esta nota).
// ===========================================================================
// package cv.pn.processmanagement.business.assinatura.controller;


// import cv.pn.processmanagement.business.assinatura.services.IAssinaturaService;
// import cv.pn.processmanagement.business.assinatura.dto.AssinaturaDto;
// import cv.pn.processmanagement.utilities.APIResponse;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/api/assinatura")
// public class AssinaturaController {

//     private final IAssinaturaService iAssinaturaService;

//     public AssinaturaController(IAssinaturaService iAssinaturaService) {
//         this.iAssinaturaService = iAssinaturaService;
//     }

//     @PostMapping
//     public ResponseEntity<APIResponse> create(@RequestBody AssinaturaDto dto) {
//         return ResponseEntity.ok(iAssinaturaService.createAssinatura(dto));
//     }
// }
