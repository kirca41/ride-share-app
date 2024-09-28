package mk.ukim.finki.rideshare.web.api;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.service.UserService;
import mk.ukim.finki.rideshare.web.converter.UserConverter;
import mk.ukim.finki.rideshare.web.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userConverter.toResponse(userService.getById(id));
    }
}
