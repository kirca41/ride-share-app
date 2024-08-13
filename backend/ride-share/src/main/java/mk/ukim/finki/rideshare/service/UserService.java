package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    User createUser(UserDetails userDetails);
}
