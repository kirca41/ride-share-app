package mk.ukim.finki.rideshare.web.response;

public record RatingResponse(
        Long id,
        Double value,
        String comment,
        RideResponse ride,
        UserResponse ratedBy
) {
}
