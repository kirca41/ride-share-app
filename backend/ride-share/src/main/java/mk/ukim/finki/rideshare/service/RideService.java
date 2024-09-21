package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.Ride;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface RideService {

    List<Ride> search(String origin, String destination, LocalDate date, Integer seats);

    List<Ride> getAllForActiveUser(Boolean includePast);

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
}
