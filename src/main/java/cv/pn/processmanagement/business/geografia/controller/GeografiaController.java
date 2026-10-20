// ===========================================================================
// CONTROLADOR COMENTADO A PEDIDO (2026-10-20): fora do Swagger
// de "Sigo dados - antigo". Para reativar, remover o prefixo "// "
// do inicio de cada linha (o bloco comeca na linha seguinte a esta nota).
// ===========================================================================
// package cv.pn.processmanagement.business.geografia.controller;

// import cv.pn.processmanagement.business.geografia.services.IGeografiaService;
// import cv.pn.processmanagement.business.geografia.dto.GeografiaDto;
// import cv.pn.processmanagement.utilities.APIResponse;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/api/geografia")
// public class GeografiaController {

//     private final IGeografiaService iGeografiaService;

//     public GeografiaController(IGeografiaService iGeografiaService) {
//         this.iGeografiaService = iGeografiaService;
//     }

//     @PostMapping
//     public ResponseEntity<APIResponse> create(@RequestBody GeografiaDto dto) {
//         return ResponseEntity.ok(iGeografiaService.createGeografia(dto));
//     }
// }
