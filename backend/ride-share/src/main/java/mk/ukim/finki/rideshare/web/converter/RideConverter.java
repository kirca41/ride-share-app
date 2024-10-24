package mk.ukim.finki.rideshare.web.converter;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.service.BookingRideManagingService;
import mk.ukim.finki.rideshare.web.response.RideResponse;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
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
                ride.getDepartureDateTime().withZoneSameInstant(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                ride.getDepartureDateTime().withZoneSameInstant(ZoneId.systemDefault()).toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                ride.getIsDepartureTimeFlexible(),
                ride.getPrice(),
                ride.getHasLuggageSpace(),
                ride.getCapacity(),
                ride.getCapacity() - bookingRideManagingService.getAllByRideAndStatusApproved(ride).stream().mapToInt(Booking::getSeatsBooked).sum(),
                ride.getIsInstantBookingEnabled(),
                ride.getIsCanceled(),
                ride.getProvider().getId(),
                ride.getProvider().getFullName()
        );
    }
}
