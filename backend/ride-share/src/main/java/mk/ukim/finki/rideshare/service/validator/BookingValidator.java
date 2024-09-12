package mk.ukim.finki.rideshare.service.validator;

import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.service.exception.RideShareServerException;
import org.springframework.stereotype.Service;

@Service
public class BookingValidator {

    public void validateCanCreateBooking(User activeUser, Ride ride, Integer seatsBooked, Integer seatsToBook, Boolean existsByRideAndUser) {
        if (ride.getCapacity() < seatsBooked + seatsToBook)
            throw new RideShareServerException(String.format("There are %d seats left for this ride", ride.getCapacity() - seatsBooked));

        if (isBookerEqualToRideProvider(activeUser, ride)) {
            throw new RideShareServerException("You cannot book a ride with yourself as a provider");
        }

        if (existsByRideAndUser) {
            throw new RideShareServerException("You have already booked/requested a place for this ride");
        }
    }

    private Boolean isBookerEqualToRideProvider(User booker, Ride ride) {
        return booker.equals(ride.getProvider());
    }
}
