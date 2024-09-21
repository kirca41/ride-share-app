package mk.ukim.finki.rideshare.web.request;

public record CreateRatingRequest(
        Long id,
        Long rideId,
        Double value,
        String comment
) {
}
