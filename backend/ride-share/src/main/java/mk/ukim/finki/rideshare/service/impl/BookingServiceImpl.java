package mk.ukim.finki.rideshare.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.config.ApplicationConstants;
import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.BookingStatus;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.repository.BookingRepository;
import mk.ukim.finki.rideshare.repository.specification.BookingSpecification;
import mk.ukim.finki.rideshare.service.BookingService;
import mk.ukim.finki.rideshare.service.BookingStatusService;
import mk.ukim.finki.rideshare.service.RideService;
import mk.ukim.finki.rideshare.service.exception.RideShareServerException;
import mk.ukim.finki.rideshare.service.helper.AuthHelperService;
import mk.ukim.finki.rideshare.service.notification.MailNotificationService;
import mk.ukim.finki.rideshare.service.validator.BookingValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final AuthHelperService authHelperService;
    private final RideService rideService;
    private final BookingStatusService bookingStatusService;
    private final BookingValidator bookingValidator;
    private MailNotificationService mailNotificationService;

    @Override
    public Booking getById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking with id = [%d] not found!".formatted(bookingId)));
    }

    @Transactional
    @Override
    public Booking create(Long rideId, Integer seatsToBook) {
        User activeUser = authHelperService.getActiveUser()
                .orElseThrow(() -> new RideShareServerException("You must be logged in to book")); // should also check Authority?
        Ride ride = rideService.getById(rideId);

        bookingValidator.validateCanCreateBooking(activeUser, ride, seatsToBook, existsByRideAndBookedBy(ride, activeUser));

        Booking booking = new Booking(
                seatsToBook,
                ZonedDateTime.now(),
                getStatusAccordingToRideIsInstantBookingEnabled(ride),
                activeUser,
                ride
        );

        booking = bookingRepository.save(booking);

        mailNotificationService.createBookingEmailNotifications(activeUser, ride);

        return booking;
    }

    private BookingStatus getStatusAccordingToRideIsInstantBookingEnabled(Ride ride) {
        if (ride.getIsInstantBookingEnabled()) {
            return bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_APPROVED);
        } else {
            return bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_NEW);
        }
    }

    @Override
    public Boolean existsByRideAndBookedBy(Ride ride, User user) {
        return bookingRepository.existsByRideAndBookedBy(ride, user);
    }

    @Override
    public Boolean existsByRideAndBookedByAndStatus(Ride ride, User bookedBy, BookingStatus bookingStatus) {
        return bookingRepository.existsByRideAndBookedByAndStatus(ride, bookedBy, bookingStatus);
    }

    @Override
    public Booking updateStatus(Long bookingId, String newStatusName) {
        Booking booking = getById(bookingId);
        BookingStatus newStatus = bookingStatusService.findByName(newStatusName);

        booking.setStatus(newStatus);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getAllForRide(Long rideId) {
        return bookingRepository.findAllByRideOrderByStatus(rideService.getById(rideId));
    }

    @Override
    public List<Booking> getAllForActiveUser(Boolean includePast) {
        User activeUser = authHelperService.getActiveUser()
                .orElseThrow(() -> new RideShareServerException("You have to be logged in to view your bookings"));

        return bookingRepository
                .findAll(
                        BookingSpecification.bookedByEqualsAndBookedAtGreaterThan(activeUser, includePast ? null : LocalDate.now())
                );
    }

    @Override
    public List<Booking> getAllByStatusAndRideDepartureTimeBefore(BookingStatus status, ZonedDateTime referenceDateTime) {
        return bookingRepository.findAllByStatusAndRide_DepartureDateTimeBefore(status, referenceDateTime);
    }
}
