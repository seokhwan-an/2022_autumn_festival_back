package likelion.festival.domain.notification.repository;

import likelion.festival.domain.notification.Notification;
import likelion.festival.domain.notification.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByNotificationType(NotificationType notificationType);
}
