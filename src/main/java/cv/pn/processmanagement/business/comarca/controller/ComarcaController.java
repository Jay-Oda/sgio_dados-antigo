package cv.pn.processmanagement.business.comarca.controller;


import cv.pn.processmanagement.business.comarca.dto.ComarcaDto;
import cv.pn.processmanagement.business.comarca.services.IComarcaService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comarca")
public class ComarcaController {

    private final IComarcaService iComarcaService;

    public ComarcaController(IComarcaService iComarcaService) {
        this.iComarcaService = iComarcaService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody ComarcaDto dto) {
        return ResponseEntity.ok(iComarcaService.createComarca(dto));
    }
}
