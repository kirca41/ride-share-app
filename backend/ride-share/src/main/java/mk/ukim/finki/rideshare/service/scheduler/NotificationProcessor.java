package mk.ukim.finki.rideshare.service.scheduler;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Notification;
import mk.ukim.finki.rideshare.service.NotificationService;
import mk.ukim.finki.rideshare.service.exception.RideShareServerException;
import mk.ukim.finki.rideshare.service.notification.MailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationProcessor {

    private final NotificationService notificationService;
    private final MailService mailService;

    @Scheduled(cron = "*/10 * * * * ?")
    public void processNotifications() {
        List<Notification> unprocessedNotifications =
                notificationService.findAllUnprocessed().stream()
                        .map(this::processNotification)
                        .toList();

        notificationService.bulkSave(unprocessedNotifications);
    }

    private Notification processNotification(Notification notification) {
        switch (notification.getNotificationType()) {
            case EMAIL -> {
                try {
                    mailService.sendTemplateEmail(notification.getRecipient(), notification.getSubject(), notification.getTemplateName(), notification.getContext());
                    notification.setIsProcessed(true);
                } catch (MessagingException | IOException e) {
                    throw new RideShareServerException(e.getMessage());
                }
            }
            case ON_PAGE -> {
                // TODO: Not implemented yet
            }
        }

        return notification;
    }

}
