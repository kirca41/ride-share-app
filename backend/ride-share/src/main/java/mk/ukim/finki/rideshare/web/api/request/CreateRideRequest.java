package mk.ukim.finki.rideshare.web.api.request;

import java.time.ZonedDateTime;

public record CreateRideRequest(
        Double originLatitude,
        Double originLongitude,
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
