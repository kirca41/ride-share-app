package mk.ukim.finki.rideshare.web.converter;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.service.BookingRideManagingService;
import mk.ukim.finki.rideshare.web.response.RideResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class RideConverter {

    private final BookingRideManagingService bookingRideManagingService;

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
                ride.getDepartureDateTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                ride.getDepartureDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                ride.getIsDepartureTimeFlexible(),
                ride.getPrice(),
                ride.getHasLuggageSpace(),
                ride.getCapacity(),
                ride.getCapacity() - bookingRideManagingService.getAllByRideAndStatusApproved(ride).size(),
                ride.getIsInstantBookingEnabled(),
                "%s %s".formatted(ride.getProvider().getFirstName(), ride.getProvider().getLastName())
        );
    }
}
