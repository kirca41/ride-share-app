package mk.ukim.finki.rideshare.model.dto;

public record UserRegisterDto(
        String firstName,
        String lastName,
        String username,
        String password,
        String confirmPassword,
        String mobileNumber
) {
}
