package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.Notification;
import mk.ukim.finki.rideshare.model.enums.NotificationType;

import java.util.List;
import java.util.Map;

public interface NotificationService {

    Notification create(NotificationType notificationType, String subject, String recipient, String templateName, Map<String, Object> context);

    List<Notification> findAllUnprocessed();

    List<Notification> bulkSave(List<Notification> notifications);
}
