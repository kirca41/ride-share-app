package mk.ukim.finki.rideshare.web.request;

public record CreateBookingRequest(
        Long rideId,
        Integer seatsToBook
) {
}
