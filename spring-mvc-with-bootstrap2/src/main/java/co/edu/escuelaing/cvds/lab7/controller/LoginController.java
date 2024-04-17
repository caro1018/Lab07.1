package co.edu.escuelaing.cvds.lab7.controller;

import co.edu.escuelaing.cvds.lab7.model.Session;
import co.edu.escuelaing.cvds.lab7.model.User;
import co.edu.escuelaing.cvds.lab7.model.UserRole;
import co.edu.escuelaing.cvds.lab7.repository.SessionRepository;
import co.edu.escuelaing.cvds.lab7.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private static final String LOGIN_PAGE = "login/login";

    private final UserRepository userRepository;

    private final SessionRepository sessionRepository;

    @Autowired
    public LoginController(
            UserRepository userRepository,
            SessionRepository sessionRepository
    ) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("")
    public String login() {
        return LOGIN_PAGE;
    }

    @PostMapping("")
    public String loginSubmit(@RequestParam Map<String, String> parameters, Model model, HttpServletResponse response) {
        User user = userRepository.findByEmail(parameters.get("email"));
        if (user == null) {
            model.addAttribute("errors", Arrays.asList("Usuario no encontrado"));
            return LOGIN_PAGE;
        } else if (!user.getPassword().equals(parameters.get("password"))) {
            model.addAttribute("errors", Arrays.asList("Contraseña incorrecta"));
            return LOGIN_PAGE;
        } else {
            Session session = new Session(UUID.randomUUID(), Instant.now(), user);
            sessionRepository.save(session);
            // create and add a cookie to the response
            Cookie cookie = new Cookie("authToken", session.getToken().toString());
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return "redirect:/login/protected/example";
        }
    }

    @PostMapping("logout")
    public String logoutSubmit(HttpServletResponse response) {
        Cookie cookie = new Cookie("authToken", null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return LOGIN_PAGE;
    }

    @GetMapping("register")
    public String register() {
        return "login/register";
    }

    @PostMapping("register")
    public String registerSubmit(@RequestParam Map<String, String> parameters) {
        User user = new User(
                parameters.get("email"),
                parameters.get("password"),
                Arrays.asList(UserRole.CLIENTE)
        );
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("protected/example")
    public String protectedExample() {
        return "login/protected";
    }

}
