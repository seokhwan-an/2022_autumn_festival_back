package likelion.festival.service.notification;

import likelion.festival.dto.notification.NotificationRequest;
import likelion.festival.dto.notification.NotificationResponse;
import likelion.festival.domain.notification.Notification;
import likelion.festival.exception.WrongNotificationId;
import likelion.festival.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationResponse getNotification(Long id) {
        final Notification notification = notificationRepository.findById(id)
                .orElseThrow(WrongNotificationId::new);
        return new NotificationResponse(notification);
    }

    public List<NotificationResponse> getNotificationsByType(final String notificationType) {
        List<Notification> notifications = notificationRepository.findAll();
        if (Objects.isNull(notificationType)) {
            return notifications.stream()
                    .map(NotificationResponse::new)
                    .collect(Collectors.toUnmodifiableList());
        }
        return notifications.stream()
                .filter(notification -> notification.isSameType(notificationType))
                .map(NotificationResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public Notification createNotification(final NotificationRequest request) {
        final Notification notification = new Notification(
                request.getTitle(),
                request.getWriter(),
                request.getContent(),
                request.getNotificationType());
        return notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(final Long id) {
        final Notification notification = notificationRepository.findById(id)
                .orElseThrow(WrongNotificationId::new);
        notificationRepository.delete(notification);
    }

    @Transactional
    public Notification updateNotification(final Long id, final NotificationRequest request) {
        final Notification notification = notificationRepository.findById(id)
                .orElseThrow(WrongNotificationId::new);
        notification.update(request.getTitle(),
                request.getWriter(),
                request.getContent(),
                request.getNotificationType());

        return notification;
    }
}
