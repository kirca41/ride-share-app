package mk.ukim.finki.rideshare.service.notification;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Message;
import mk.ukim.finki.rideshare.model.enums.NotificationType;
import mk.ukim.finki.rideshare.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OnPageNotificationService {

    private final NotificationService notificationService;

    public void createNewMessageOnPageNotification(Message message, String otherParticipantId) {
        Map<String, Object> context = new HashMap<>();
        context.put("senderFullName", message.getSender().getFullName());
        context.put("content", message.getContent());
        context.put("sentOnDate", message.getTimestamp().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        context.put("sentAtTime", message.getTimestamp().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        context.put("chatUuid", message.getChat().getUuid());

        notificationService.create(
                NotificationType.ON_PAGE,
                null,
                otherParticipantId,
                null,
                context
        );
    }
}
