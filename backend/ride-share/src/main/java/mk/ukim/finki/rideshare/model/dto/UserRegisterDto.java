package mk.ukim.finki.rideshare.model.dto;

public record UserRegisterDto(
        String username,
        String password,
        String confirmPassword,
        String mobileNumber
) {
}
