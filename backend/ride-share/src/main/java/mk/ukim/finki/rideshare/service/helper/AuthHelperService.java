package mk.ukim.finki.rideshare.service.helper;

import mk.ukim.finki.rideshare.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthHelperService {

    public Optional<User> getActiveUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null) {
            return Optional.of((User) authentication.getPrincipal());
        }

        return Optional.empty();
    }
}
