package likelion.festival.dto.notification;

import likelion.festival.domain.image.Image;
import likelion.festival.domain.notification.Notification;
import likelion.festival.domain.notification.NotificationType;
import likelion.festival.dto.image.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class NotificationResponse {

    private Long id;

    private String title;

    private String writer;

    private String content;

    private NotificationType notificationType;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<ImageResponse> images;

    public NotificationResponse(final Notification notification) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.writer = notification.getWriter();
        this.content = notification.getContent();
        this.notificationType = notification.getNotificationType();
        this.createdAt = notification.getCreatedDateTime();
        this.modifiedAt = notification.getModifiedDateTime();
    }

}
