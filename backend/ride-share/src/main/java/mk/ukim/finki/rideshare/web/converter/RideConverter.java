package mk.ukim.finki.rideshare.web.converter;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.config.ApplicationConstants;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.service.BookingService;
import mk.ukim.finki.rideshare.service.BookingStatusService;
import mk.ukim.finki.rideshare.web.response.RideResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class RideConverter {

    private final BookingService bookingService;
    private final BookingStatusService bookingStatusService;

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
                ride.getDepartureTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                ride.getDepartureTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                ride.getIsDepartureTimeFlexible(),
                ride.getPrice(),
                ride.getHasLuggageSpace(),
                ride.getCapacity(),
                ride.getCapacity() -
                        bookingService.getAllByRideAndStatus(
                                ride,
                                bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_APPROVED)
                        ).size(),
                ride.getIsInstantBookingEnabled(),
                "%s %s".formatted(ride.getProvider().getFirstName(), ride.getProvider().getLastName())
        );
    }
}
