package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface RideService {

    List<Ride> search(String origin, String destination, LocalDate date, Integer seats, Sort sort);

    List<Ride> getAllForActiveUser(Boolean includePast);

    Boolean hasRideEnoughSeatsLeft(Ride ride, Integer seats);

    Ride getById(Long id);

    Ride getByUuid(UUID uuid);

    Ride create(String origin,
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
                Boolean isInstantBookingEnabled);

    List<Ride> getAllByOriginLikeAndDestinationLike(String origin, String destination);

    Ride cancel(Long rideId);

    Long getNumberOfCancellationsByProviderInTheLastMonth(User provider);
}
