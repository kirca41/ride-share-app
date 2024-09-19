package mk.ukim.finki.rideshare.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Notification;
import mk.ukim.finki.rideshare.model.enums.NotificationType;
import mk.ukim.finki.rideshare.repository.NotificationRepository;
import mk.ukim.finki.rideshare.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification create(NotificationType notificationType, String subject, String recipient, String templateName, Map<String, Object> context) {
        return notificationRepository.save(
                new Notification(
                        notificationType, subject, recipient, templateName, context, false
                )
        );
    }

    @Override
    public List<Notification> findAllUnprocessed() {
        return notificationRepository.findAllByIsProcessed(false);
    }

    @Override
    public List<Notification> bulkSave(List<Notification> notifications) {
        return notificationRepository.saveAll(notifications);
    }
}
