package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.Authority;
import mk.ukim.finki.rideshare.model.User;

public interface UserService {

    User createUser(String username, String password, String mobileNumber, Authority authority);
}
