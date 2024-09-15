package mk.ukim.finki.rideshare.web.response;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String fullName,
        String mobileNumber,
        String joinedOnDate
) {
}
