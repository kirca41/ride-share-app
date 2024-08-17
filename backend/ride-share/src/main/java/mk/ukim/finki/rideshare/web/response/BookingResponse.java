package mk.ukim.finki.rideshare.web.response;

public record BookingResponse(
        String statusName,
        String statusPrettyName,
        Long bookedById,
        String bookedByUsername,
        Long rideId
) {
}