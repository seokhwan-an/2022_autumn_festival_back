package likelion.festival.domain.notification;

import java.util.Arrays;

public enum NotificationType {
    ALL,
    FESTIVAL,
    EVENT,
    ECT;

    public static NotificationType find(final String type) {
        return Arrays.stream(NotificationType.values())
                .filter(notificationType -> notificationType.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 타입입니다."));
    }
}
