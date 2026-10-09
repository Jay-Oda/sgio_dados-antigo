package cv.pn.processmanagement.business.tipoAuto.controller;


import cv.pn.processmanagement.business.tipoAuto.dto.TiposAutoDto;
import cv.pn.processmanagement.business.tipoAuto.services.ITiposAutoService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tiposauto")
public class TiposAutoController {

    private final ITiposAutoService iTiposAutoService;

    public TiposAutoController(ITiposAutoService iTiposAutoService) {
        this.iTiposAutoService = iTiposAutoService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody TiposAutoDto dto) {
        return ResponseEntity.ok(iTiposAutoService.createTiposAuto(dto));
    }
}
