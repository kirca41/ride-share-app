package mk.ukim.finki.rideshare.web.converter;

import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.web.response.UserResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class UserConverter {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getFullName(),
                user.getMobileNumber(),
                user.getJoinedOn().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
        );
    }
}
