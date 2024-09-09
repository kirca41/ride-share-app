package mk.ukim.finki.rideshare.web.request;

import java.time.LocalDateTime;

public record CreateRideRequest(
        String origin,
        Double originLatitude,
        Double originLongitude,
        String destination,
        Double destinationLatitude,
        Double destinationLongitude,
        Boolean isDoorToDoor,
        LocalDateTime departureTime,
        Boolean isDepartureTimeFlexible,
        Double price,
        Boolean hasLuggageSpace,
        Integer capacity,
        Boolean isInstantBookingEnabled
) {
}
