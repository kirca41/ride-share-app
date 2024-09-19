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

    public void createBookingConfirmationEmailNotification(User activeUser, Ride ride) {
        Map<String, Object> context = new HashMap<>();
        context.put("userFullName", activeUser.getFullName());
        context.put("isInstantBookingEnabled", ride.getIsInstantBookingEnabled());
        context.put("providerFullName", ride.getProvider().getFullName());
        context.put("departureDate", ride.getDepartureDateTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        context.put("departureTime", ride.getDepartureDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        context.put("rideOrigin", ride.getOrigin());
        context.put("rideDestination", ride.getDestination());
        context.put("ridePrice", ride.getPrice());

        notificationService.create(
                NotificationType.EMAIL,
                BOOKING_CONFIRMATION_EMAIL_SUBJECT,
                activeUser.getUsername(),
                BOOKING_CONFIRMATION_EMAIL_TEMPLATE_NAME,
                context
        );
    }
}
