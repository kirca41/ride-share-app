package mk.ukim.finki.rideshare.service.scheduler;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.config.ApplicationConstants;
import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.BookingStatus;
import mk.ukim.finki.rideshare.service.BookingService;
import mk.ukim.finki.rideshare.service.BookingStatusService;
import mk.ukim.finki.rideshare.service.notification.MailNotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RideRatingScheduler {

    private final BookingService bookingService;
    private final BookingStatusService bookingStatusService;
    private final MailNotificationService mailNotificationService;

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    public void processPastRides() {
        BookingStatus approved = bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_APPROVED);
        List<Booking> bookings = bookingService.getAllByStatusAndRideDepartureTimeBefore(approved, ZonedDateTime.now(ZoneId.systemDefault()));

        bookings.forEach(this::createRideRatingEmailNotification);
    }

    // TODO: start transaction programmatically
    private void createRideRatingEmailNotification(Booking booking) {
        mailNotificationService.createRideRatingEmailNotification(booking.getRide(), booking.getBookedBy());
    }

}
