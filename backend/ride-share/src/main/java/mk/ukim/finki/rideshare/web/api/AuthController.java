package mk.ukim.finki.rideshare.web.api;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.model.dto.AuthenticationDto;
import mk.ukim.finki.rideshare.model.dto.UserLoginDto;
import mk.ukim.finki.rideshare.model.dto.UserRegisterDto;
import mk.ukim.finki.rideshare.service.auth.AuthenticationService;
import mk.ukim.finki.rideshare.service.helper.AuthHelperService;
import mk.ukim.finki.rideshare.web.converter.UserConverter;
import mk.ukim.finki.rideshare.web.response.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/public/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final AuthHelperService authHelperService;
    private final UserConverter userConverter;

    // Validate dtos
    @PostMapping("/register")
    public AuthenticationDto register(@RequestBody UserRegisterDto userRegisterDto) {
        return authenticationService.register(userRegisterDto);
    }

    @PostMapping("/login")
    public AuthenticationDto login(@RequestBody UserLoginDto userLoginDto) {
        return authenticationService.authenticate(userLoginDto);
    }

    @GetMapping("/active-user")
    public UserResponse getActiveUser() {
        Optional<User> activeUser = authHelperService.getActiveUser();

        return activeUser.map(userConverter::toResponse).orElse(null);
    }
}
