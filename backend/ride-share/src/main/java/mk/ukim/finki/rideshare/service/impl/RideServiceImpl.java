package mk.ukim.finki.rideshare.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.repository.RideRepository;
import mk.ukim.finki.rideshare.repository.specification.RideSpecification;
import mk.ukim.finki.rideshare.service.BookingRideManagingService;
import mk.ukim.finki.rideshare.service.RideService;
import mk.ukim.finki.rideshare.service.exception.RideShareServerException;
import mk.ukim.finki.rideshare.service.helper.AuthHelperService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final AuthHelperService authHelperService;
    private final BookingRideManagingService bookingRideManagingService;

    @Override
    public List<Ride> search(String origin, String destination, LocalDate date, Integer seats, Sort sort) {
        List<Ride> rides = rideRepository.findAll(
                RideSpecification
                        .hasOriginLikeAndDestinationLikeAndDepartureDateTime(
                                origin, destination, date
                        ),
                sort
        );

        if (seats == null)
            return rides;

        return rides.stream()
                .filter(r -> hasRideEnoughSeatsLeft(r, seats))
                .toList();
    }

    @Override
    public List<Ride> getAllForActiveUser(Boolean includePast) {
        User activeUser = authHelperService.getActiveUser()
                .orElseThrow(() -> new RideShareServerException("User not found"));

        return rideRepository.findAll(RideSpecification.providerEqualsAndDepartureTimeGreaterThan(
                activeUser,
                includePast ? null : LocalDate.now()
        ))
                .stream().sorted(Comparator.comparing(Ride::getDepartureDateTime, Comparator.reverseOrder()))
                .toList();
    }

    private Boolean hasRideEnoughSeatsLeft(Ride ride, Integer seats) {
        return ride.getCapacity() -
                bookingRideManagingService.getAllByRideAndStatusApproved(ride).stream().mapToInt(Booking::getSeatsBooked).sum() >= seats;
    }

    @Override
    public Ride getById(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found exception"));
    }

    @Override
    public Ride getByUuid(UUID uuid) {
        return rideRepository.findByUuid(uuid)
                .orElseThrow(() -> new RideShareServerException("Ride with uuid %s does not exist".formatted(uuid)));
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
                .orElseThrow(() -> new RideShareServerException("You must be logged in to publish a new ride")); // Should also check Authority
        Ride ride = new Ride(
                UUID.randomUUID(),
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
