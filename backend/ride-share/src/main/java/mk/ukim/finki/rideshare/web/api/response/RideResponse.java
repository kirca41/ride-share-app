package mk.ukim.finki.rideshare.web.api.response;

import java.time.ZonedDateTime;

public record RideResponse(
        Long id,
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
        Boolean isInstantBookingEnabled,
        String providerUsername
) {
}
