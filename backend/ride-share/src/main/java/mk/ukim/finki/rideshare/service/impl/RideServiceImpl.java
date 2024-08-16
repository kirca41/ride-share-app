package mk.ukim.finki.rideshare.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.repository.RideRepository;
import mk.ukim.finki.rideshare.service.RideService;
import mk.ukim.finki.rideshare.service.helper.AuthHelperService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final AuthHelperService authHelperService;

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
                .orElseThrow(() -> new RuntimeException("You must be logged in to publish a new ride"));
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

    @Override
    public List<Ride> getAll() {
        return rideRepository.findAll();
    }
}
