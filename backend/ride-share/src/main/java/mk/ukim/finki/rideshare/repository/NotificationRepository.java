package mk.ukim.finki.rideshare.repository;

import mk.ukim.finki.rideshare.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByIsProcessed(Boolean isProcessed);
}
