package mk.ukim.finki.rideshare.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.repository.UserRepository;
import mk.ukim.finki.rideshare.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(UserDetails userDetails) {
        return userRepository.save(new User(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.isAccountNonExpired(),
                userDetails.isAccountNonLocked(),
                userDetails.isEnabled()
        ));
    }
}
