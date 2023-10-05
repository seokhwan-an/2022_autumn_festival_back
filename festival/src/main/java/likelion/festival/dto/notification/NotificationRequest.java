package likelion.festival.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotificationRequest {

    private String title;

    private String writer;

    private String content;

    private String notificationType;
}
