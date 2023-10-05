package likelion.festival.dto.notification;

import likelion.festival.domain.image.Image;
import likelion.festival.domain.notification.Notification;
import likelion.festival.domain.notification.NotificationType;
import likelion.festival.dto.image.ImageResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NotificationResponse {

    private Long id;

    private String title;

    private String writer;

    private String content;

    private NotificationType notificationType;

    private LocalDateTime createdDateTime;

    private LocalDateTime modifiedDateTime;

    private List<ImageResponse> images;

    public NotificationResponse(final Notification notification) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.writer = notification.getWriter();
        this.content = notification.getContent();
        this.notificationType = notification.getNotificationType();
        this.createdDateTime = notification.getCreatedDateTime();
        this.modifiedDateTime = notification.getModifiedDateTime();
    }

}
