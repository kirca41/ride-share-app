package mk.ukim.finki.rideshare.web.response;

public record BookingResponse(
        Long id,
        String statusName,
        String statusPrettyName,
        Long bookedById,
        String bookedByUsername,
        RideResponse ride
) {
}