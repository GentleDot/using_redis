package net.gentledot.client_spring.home;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    public Map<String, String> home(HttpSession session) {
        String visitKey = "visits";
        Integer visits = (Integer) session.getAttribute(visitKey);
        if (visits == null) {
            visits = 0;
        }
        session.setAttribute(visitKey, ++visits);
        return Map.of("session id", session.getId(), visitKey, String.valueOf(visits));
    }
}
