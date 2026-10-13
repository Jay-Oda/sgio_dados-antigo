package cv.pn.processmanagement.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Controller
public class HomePageController {

    Logger logger = LogManager.getLogger(HomePageController.class);

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {

        // Voltar à página de login (ex.: seta "voltar" do browser) termina a sessão: para regressar ao
        // Swagger, mesmo com a seta "avançar", é preciso autenticar de novo
        if (request.getSession(false) != null) {
            Authentication sessao = SecurityContextHolder.getContext().getAuthentication();
            new SecurityContextLogoutHandler().logout(request, response, sessao);
            new CookieClearingLogoutHandler("JSESSIONID").logout(request, response, sessao);
        }

        logger.warn("Authentication: " + LocalDateTime.now());
        return "login";

    }

    /** http://localhost:{porta}/ abre o Swagger; sem sessão, o Spring Security pede primeiro o login. */
    @RequestMapping("/")
    public String raiz() {
        return "redirect:/api/swagger-ui/index.html";
    }

}
