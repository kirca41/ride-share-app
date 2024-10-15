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
import java.time.ZoneId;
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

    @Override
    @Transactional
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
    public Booking approve(Long bookingId) {
        Booking booking = getById(bookingId);
        BookingStatus bookingStatusNew = bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_NEW);
        if (!bookingStatusNew.equals(booking.getStatus())) {
            throw new RideShareServerException("Only bookings with status %s can be approved".formatted(bookingStatusNew.getPrettyName()));
        }

        if (!rideService.hasRideEnoughSeatsLeft(booking.getRide(), booking.getSeatsBooked())) {
            throw new RideShareServerException("Not enough seats left for this ride");
        }

        booking = updateStatus(booking, ApplicationConstants.BOOKING_STATUS_APPROVED);
        mailNotificationService.createBookingRequestOutcomeEmailNotification(
                booking.getBookedBy(), booking.getRide(), booking.getStatus()
        );

        return booking;
    }

    @Override
    @Transactional
    public Booking cancel(Long bookingId) {
        Booking booking = getById(bookingId);
        BookingStatus bookingStatusDeclined = bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_DECLINED);
        if (bookingStatusDeclined.equals(booking.getStatus())) {
            throw new RideShareServerException("You cannot cancel an already declined booking");
        }

        if (lessThan24HoursBeforeRideDepartureDateTime(booking.getRide().getDepartureDateTime())) {
            throw new RideShareServerException("You cannot cancel a booking less than 24 hours before departure time");
        }

        Booking updatedBooking = updateStatus(booking, ApplicationConstants.BOOKING_STATUS_CANCELED);
        mailNotificationService.createBookingCancellationEmailNotification(booking.getRide(), booking.getBookedBy());
        return updatedBooking;
    }

    private Boolean lessThan24HoursBeforeRideDepartureDateTime(ZonedDateTime rideDepartureDateTime) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        return rideDepartureDateTime.minusHours(24).isBefore(now);
    }

    @Override
    public Booking decline(Long bookingId) {
        Booking booking = getById(bookingId);
        BookingStatus bookingStatusNew = bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_NEW);
        if (!bookingStatusNew.equals(booking.getStatus())) {
            throw new RideShareServerException("Only bookings with status %s can be declined".formatted(bookingStatusNew.getPrettyName()));
        }

        booking = updateStatus(booking, ApplicationConstants.BOOKING_STATUS_DECLINED);
        mailNotificationService.createBookingRequestOutcomeEmailNotification(
                booking.getBookedBy(), booking.getRide(), booking.getStatus()
        );

        return booking;
    }

    @Override
    public Booking updateStatus(Booking booking, String newStatusName) {
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

    @Override
    public Long getNumberOfCancellationsByBookedByInTheLastMonth(User bookedBy) {
        BookingStatus canceled = bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_CANCELED);
        ZonedDateTime to = ZonedDateTime.now(ZoneId.systemDefault());
        ZonedDateTime from = to.minusMonths(1);

        return bookingRepository.countByStatusAndBookedByAndBookedAtBetween(canceled, bookedBy, from, to);
    }
}
