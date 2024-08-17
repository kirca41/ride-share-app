package mk.ukim.finki.rideshare.web.request;

import java.time.ZonedDateTime;

public record CreateRideRequest(
        String origin,
        Double originLatitude,
        Double originLongitude,
        String destination,
        Double destinationLatitude,
        Double destinationLongitude,
        Boolean isDoorToDoor,
        ZonedDateTime departureTime,
        Boolean isDepartureTimeFlexible,
        Double price,
        Boolean hasLuggageSpace,
        Integer capacity,
        Boolean isInstantBookingEnabled
) {
}
