package mk.ukim.finki.rideshare.web.response;

public record RideResponse(
        Long id,
        String origin,
        Double originLatitude,
        Double originLongitude,
        String destination,
        Double destinationLatitude,
        Double destinationLongitude,
        Boolean isDoorToDoor,
        String departureDate,
        String departureTime,
        Boolean isDepartureTimeFlexible,
        Double price,
        Boolean hasLuggageSpace,
        Integer capacity,
        Integer seatsLeft,
        Boolean isInstantBookingEnabled,
        String providerFullName
) {
}
