package mk.ukim.finki.rideshare.service.notification;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.model.enums.NotificationType;
import mk.ukim.finki.rideshare.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MailNotificationService {

    private final NotificationService notificationService;

    private static final String BOOKING_CONFIRMATION_EMAIL_SUBJECT = "Booking Confirmation";
    private static final String BOOKING_CONFIRMATION_EMAIL_TEMPLATE_NAME = "booking_confirmation";

    private static final String BOOKING_ALERT_EMAIL_SUBJECT = "New booking for ride";
    private static final String BOOKING_ALERT_EMAIL_TEMPLATE_NAME = "booking_alert";

    private static final String RIDE_RATING_EMAIL_SUBJECT = "Tell us how it went";
    private static final String RIDE_RATING_EMAIL_TEMPLATE_NAME = "ride_rating";

    private static final String BOOKING_CANCELLATION_EMAIL_SUBJECT = "Ride booking canceled";
    private static final String BOOKING_CANCELLATION_EMAIL_TEMPLATE_NAME = "booking_cancellation";

    public void createBookingEmailNotifications(User activeUser, Ride ride) {
        Map<String, Object> context = new HashMap<>();
        context.put("userFullName", activeUser.getFullName());
        context.put("isInstantBookingEnabled", ride.getIsInstantBookingEnabled());
        context.put("providerFullName", ride.getProvider().getFullName());
        context.put("departureDate", ride.getDepartureDateTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        context.put("departureTime", ride.getDepartureDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        context.put("rideOrigin", ride.getOrigin());
        context.put("rideDestination", ride.getDestination());
        context.put("ridePrice", ride.getPrice());
        context.put("rideId", ride.getId());

        createBookingConfirmationEmailNotification(activeUser.getUsername(), context);
        createNewBookingAlertEmailNotification(activeUser.getUsername(), context);
    }

    private void createBookingConfirmationEmailNotification(String recipient, Map<String, Object> context) {
        notificationService.create(
                NotificationType.EMAIL,
                BOOKING_CONFIRMATION_EMAIL_SUBJECT,
                recipient,
                BOOKING_CONFIRMATION_EMAIL_TEMPLATE_NAME,
                context
        );
    }

    private void createNewBookingAlertEmailNotification(String recipient, Map<String, Object> context) {
        notificationService.create(
                NotificationType.EMAIL,
                BOOKING_ALERT_EMAIL_SUBJECT,
                recipient,
                BOOKING_ALERT_EMAIL_TEMPLATE_NAME,
                context
        );
    }

    public void createRideRatingEmailNotification(Ride ride, User bookedBy) {
        Map<String, Object> context = new HashMap<>();
        context.put("userFullName", bookedBy.getFullName());
        context.put("providerFullName", ride.getProvider().getFullName());
        context.put("rideUuid", ride.getUuid());

        notificationService.create(
                NotificationType.EMAIL,
                RIDE_RATING_EMAIL_SUBJECT,
                bookedBy.getUsername(),
                RIDE_RATING_EMAIL_TEMPLATE_NAME,
                context
        );
    }

    public void createBookingCancellationEmailNotification(Ride ride, User bookedBy) {
        Map<String, Object> context = new HashMap<>();
        context.put("userFullName", bookedBy.getFullName());
        context.put("providerFullName", ride.getProvider().getFullName());
        context.put("departureDate", ride.getDepartureDateTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        context.put("departureTime", ride.getDepartureDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        context.put("rideOrigin", ride.getOrigin());
        context.put("rideDestination", ride.getDestination());
        context.put("ridePrice", ride.getPrice());
        context.put("rideId", ride.getId());

        notificationService.create(
                NotificationType.EMAIL,
                BOOKING_CANCELLATION_EMAIL_SUBJECT,
                ride.getProvider().getUsername(),
                BOOKING_CANCELLATION_EMAIL_TEMPLATE_NAME,
                context
        );
    }
}
