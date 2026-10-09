package cv.pn.processmanagement.business.ocorrencia.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/ping")
    public String ping() {
        return "OK 8082";
    }

    @PostMapping("/ping")
    public String pingPost(@RequestBody(required = false) String body) {
        return "POST OK 8082";
    }
}
