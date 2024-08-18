package mk.ukim.finki.rideshare.service.auth;


import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.config.ApplicationConstants;
import mk.ukim.finki.rideshare.model.Authority;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.model.dto.AuthenticationDto;
import mk.ukim.finki.rideshare.model.dto.UserLoginDto;
import mk.ukim.finki.rideshare.model.dto.UserRegisterDto;
import mk.ukim.finki.rideshare.service.AuthorityService;
import mk.ukim.finki.rideshare.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private UserDetailsService userDetailsService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private AuthorityService authorityService;

    public AuthenticationDto register(UserRegisterDto userRegisterDto) {
        Authority authority = authorityService.getByAuthority(ApplicationConstants.AUTHORITY_ADMIN);
        User user = this.userService.createUser(
                userRegisterDto.username(),
                passwordEncoder.encode(userRegisterDto.password()),
                userRegisterDto.mobileNumber(),
                authority
        );

        String jwtToken = jwtService.generateToken(Map.of(), user);
        return new AuthenticationDto(jwtToken);
    }

    public AuthenticationDto authenticate(UserLoginDto userLoginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.username(),
                        userLoginDto.password()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginDto.username());
        String jwtToken = jwtService.generateToken(Map.of(), userDetails);
        return new AuthenticationDto(jwtToken);
    }
}