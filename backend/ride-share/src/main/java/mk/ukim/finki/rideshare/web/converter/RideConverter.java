package mk.ukim.finki.rideshare.web.converter;

import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.web.response.RideResponse;
import org.springframework.stereotype.Component;

@Component
public class RideConverter {

    public RideResponse toResponse(Ride ride) {
        return new RideResponse(
                ride.getId(),
                ride.getOrigin(),
                ride.getOriginLatitude(),
                ride.getOriginLongitude(),
                ride.getDestination(),
                ride.getDestinationLatitude(),
                ride.getDestinationLongitude(),
                ride.getIsDoorToDoor(),
                ride.getDepartureTime(),
                ride.getIsDepartureTimeFlexible(),
                ride.getPrice(),
                ride.getHasLuggageSpace(),
                ride.getCapacity(),
                ride.getIsInstantBookingEnabled(),
                ride.getProvider().getUsername()
        );
    }
}
