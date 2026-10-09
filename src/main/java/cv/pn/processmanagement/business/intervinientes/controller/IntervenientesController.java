package cv.pn.processmanagement.business.intervinientes.controller;


import com.google.gson.Gson;
import cv.pn.processmanagement.business.intervinientes.dto.IntervenientesDto;
import cv.pn.processmanagement.business.intervinientes.services.IIntervenientesService;
import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/intervenientes")
public class IntervenientesController {

    private final IIntervenientesService iIntervenientesService;

    public IntervenientesController(IIntervenientesService iIntervenientesService) {
        this.iIntervenientesService = iIntervenientesService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody IntervenientesDto dto) {

        System.out.println("RECEBIDO DO 8088 → " + new Gson().toJson(dto));

        return ResponseEntity.ok(iIntervenientesService.createIntervinientes(dto));
    }
}
