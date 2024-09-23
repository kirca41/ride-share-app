package mk.ukim.finki.rideshare.service.notification;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Notification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BellService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SimpUserRegistry simpUserRegistry;

    public Boolean sendBellNotificationIfSubscriptionExists(Notification notification) {
        String destination = "/topic/bell/" + notification.getRecipient();
        Boolean subscriptionToDestinationExists = subscriptionToDestinationExists(destination);
        if (subscriptionToDestinationExists) {
            BellNotificationResponse response = new BellNotificationResponse(
                    notification.getContext().get("senderFullName").toString(),
                    notification.getContext().get("content").toString(),
                    notification.getContext().get("sentOnDate").toString(),
                    notification.getContext().get("sentAtTime").toString(),
                    notification.getContext().get("chatUuid").toString()
            );
            simpMessagingTemplate.convertAndSend(destination, response);
        }

        return subscriptionToDestinationExists;
    }

    private Boolean subscriptionToDestinationExists(String destination) {
        return simpUserRegistry.getUsers().stream()
                .flatMap(user -> user.getSessions().stream())
                .flatMap(session -> session.getSubscriptions().stream())
                .anyMatch(subscription -> subscription.getDestination().equals(destination));
    }
}

record BellNotificationResponse(
        String senderFullName,
        String content,
        String sentOnDate,
        String sentAtTime,
        String chatUuid
) {}
