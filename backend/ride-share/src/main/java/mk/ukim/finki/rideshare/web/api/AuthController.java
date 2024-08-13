package mk.ukim.finki.rideshare.web.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @PostMapping("/logout")
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
    }
}
