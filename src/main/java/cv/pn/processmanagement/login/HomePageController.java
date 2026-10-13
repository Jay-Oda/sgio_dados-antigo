package cv.pn.processmanagement.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
public class HomePageController {

    Logger logger = LogManager.getLogger(HomePageController.class);

    @RequestMapping("/login")
    public String login() {

        logger.warn("Authentication: " + LocalDateTime.now());
        return "login";

    }

    /** http://localhost:{porta}/ abre o Swagger; sem sessão, o Spring Security pede primeiro o login. */
    @RequestMapping("/")
    public String raiz() {
        return "redirect:/api/swagger-ui/index.html";
    }

}
