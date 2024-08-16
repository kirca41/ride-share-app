package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.Ride;

import java.time.ZonedDateTime;
import java.util.List;

public interface RideService {

    Ride create(Double originLatitude,
                Double originLongitude,
                Double destinationLatitude,
                Double destinationLongitude,
                Boolean isDoorToDoor,
                ZonedDateTime departureTime,
                Boolean isDepartureTimeFlexible,
                Double price,
                Boolean hasLuggageSpace,
                Integer capacity,
                Boolean isInstantBookingEnabled);

    List<Ride> getAll();
}
