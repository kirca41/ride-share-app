package mk.ukim.finki.rideshare.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.config.ApplicationConstants;
import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.BookingStatus;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.repository.BookingRepository;
import mk.ukim.finki.rideshare.service.BookingService;
import mk.ukim.finki.rideshare.service.BookingStatusService;
import mk.ukim.finki.rideshare.service.RideService;
import mk.ukim.finki.rideshare.service.helper.AuthHelperService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final AuthHelperService authHelperService;
    private final RideService rideService;
    private final BookingStatusService bookingStatusService;

    @Override
    public Booking getById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking with id = [%d] not found!".formatted(bookingId)));
    }

    @Override
    public Booking create(Long rideId) {
        User activeUser = authHelperService.getActiveUser()
                .orElseThrow(() -> new RuntimeException("You must be logged in to book")); // should also check Authority
        Ride ride = rideService.getById(rideId);
        // Should I check for any kind of status so as not to let the user book again after being declined?
        if (existsWithStatusApprovedAndRideAndUser(ride, activeUser)) {
            throw new RuntimeException("You have already booked a place for this ride");
        }
        Booking booking = new Booking(
                getStatusAccordingToRideIsInstantBookingEnabled(ride),
                activeUser,
                ride
        );

        return bookingRepository.save(booking);
    }

    private BookingStatus getStatusAccordingToRideIsInstantBookingEnabled(Ride ride) {
        if (ride.getIsInstantBookingEnabled()) {
            return bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_APPROVED);
        } else {
            return bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_NEW);
        }
    }

    @Override
    public Boolean existsWithStatusApprovedAndRideAndUser(Ride ride, User user) {
        BookingStatus bookingStatus = bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_APPROVED);
        return bookingRepository.existsByStatusAndRideAndBookedBy(bookingStatus, ride, user);
    }

    @Override
    public Booking updateStatus(Long bookingId, String newStatusName) {
        Booking booking = getById(bookingId);
        BookingStatus newStatus = bookingStatusService.findByName(newStatusName);

        booking.setStatus(newStatus);
        return bookingRepository.save(booking);
    }
}
