package mk.ukim.finki.rideshare.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.model.Authority;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.repository.UserRepository;
import mk.ukim.finki.rideshare.service.UserService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(String firstName, String lastName, String username, String password, String mobileNumber, Authority authority) {
        return userRepository.save(
                new User(firstName, lastName, username, password, mobileNumber, authority)
        );
    }
}
