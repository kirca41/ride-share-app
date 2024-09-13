package mk.ukim.finki.rideshare.service.validator;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.config.ApplicationConstants;
import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.repository.BookingRepository;
import mk.ukim.finki.rideshare.service.BookingStatusService;
import mk.ukim.finki.rideshare.service.exception.RideShareServerException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookingValidator {

    private final BookingRepository bookingRepository;
    private final BookingStatusService bookingStatusService;

    public void validateCanCreateBooking(User activeUser, Ride ride, Integer seatsToBook, Boolean existsByRideAndUser) {
        Integer seatsBooked = getNumberOfSeatsBookedForRide(ride);

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

    private Integer getNumberOfSeatsBookedForRide(Ride ride) {
        return bookingRepository.findAllByRideAndStatus(ride, bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_APPROVED))
                .stream()
                .mapToInt(Booking::getSeatsBooked)
                .sum();
    }
}
