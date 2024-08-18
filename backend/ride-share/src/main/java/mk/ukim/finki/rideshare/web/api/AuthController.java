package mk.ukim.finki.rideshare.web.api;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.model.dto.AuthenticationDto;
import mk.ukim.finki.rideshare.model.dto.UserLoginDto;
import mk.ukim.finki.rideshare.model.dto.UserRegisterDto;
import mk.ukim.finki.rideshare.service.auth.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    // Validate dtos
    @PostMapping("/register")
    public AuthenticationDto register(@RequestBody UserRegisterDto userRegisterDto) {
        return authenticationService.register(userRegisterDto);
    }

    @PostMapping("/login")
    public AuthenticationDto login(@RequestBody UserLoginDto userLoginDto) {
        return authenticationService.authenticate(userLoginDto);
    }
}
