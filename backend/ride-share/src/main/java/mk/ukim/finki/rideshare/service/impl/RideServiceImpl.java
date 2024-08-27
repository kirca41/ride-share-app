package mk.ukim.finki.rideshare.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.repository.RideRepository;
import mk.ukim.finki.rideshare.repository.specification.RideSpecification;
import mk.ukim.finki.rideshare.service.BookingRideManagingService;
import mk.ukim.finki.rideshare.service.RideService;
import mk.ukim.finki.rideshare.service.helper.AuthHelperService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final AuthHelperService authHelperService;
    private final BookingRideManagingService bookingRideManagingService;

    @Override
    public List<Ride> search(String origin, String destination, LocalDate date, Integer seats) {
        List<Ride> rides = rideRepository.findAll(
                RideSpecification
                        .hasOriginLikeAndDestinationLikeAndDepartureDateTime(
                                origin, destination, date
                        )
        );

        if (seats == null)
            return rides;

        return rides.stream()
                .filter(r -> hasRideEnoughSeatsLeft(r, seats))
                .toList();
    }

    private Boolean hasRideEnoughSeatsLeft(Ride ride, Integer seats) {
        return ride.getCapacity() -
                bookingRideManagingService.getAllByRideAndStatusApproved(ride).size() >= seats;
    }

    @Override
    public Ride getById(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found exception"));
    }

    @Override
    public Ride create(String origin,
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
                       Boolean isInstantBookingEnabled) {
        User activeUser = authHelperService.getActiveUser()
                .orElseThrow(() -> new RuntimeException("You must be logged in to publish a new ride")); // Should also check Authority
        Ride ride = new Ride(
                origin,
                originLatitude,
                originLongitude,
                destination,
                destinationLatitude,
                destinationLongitude,
                isDoorToDoor,
                departureTime,
                isDepartureTimeFlexible,
                price,
                hasLuggageSpace,
                capacity,
                isInstantBookingEnabled,
                activeUser
        );

        return rideRepository.save(ride);
    }
}
