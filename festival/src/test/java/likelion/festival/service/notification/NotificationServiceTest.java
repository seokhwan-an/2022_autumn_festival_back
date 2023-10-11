package likelion.festival.service.notification;

import likelion.festival.domain.notification.Notification;
import likelion.festival.domain.notification.NotificationType;
import likelion.festival.domain.notification.repository.NotificationRepository;
import likelion.festival.dto.notification.NotificationRequest;
import likelion.festival.dto.notification.NotificationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class NotificationServiceTest {

    @Autowired
    private NotificationRepository notificationRepository;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService(notificationRepository);
    }

    @DisplayName("공지사항을 생성한다.")
    @Test
    void create_notification() {
        // given
        final NotificationRequest notificationRequest = new NotificationRequest("공지", "작성자", "공지 내용", "all");

        // when
        final Notification result = notificationService.createNotification(notificationRequest);

        // then
        assertThat(result.getTitle()).isEqualTo(notificationRequest.getTitle());
        assertThat(result.getWriter()).isEqualTo(notificationRequest.getWriter());
        assertThat(result.getContent()).isEqualTo(notificationRequest.getContent());
        assertThat(result.getNotificationType()).isEqualTo(NotificationType.ALL);
    }

    @DisplayName("공지사항을 단일 조회한다.")
    @Test
    void read_notification() {
        // given
        final Notification notification = new Notification("공지", "작성자", "공지 내용", "all");
        notificationRepository.save(notification);

        // when
        final NotificationResponse result = notificationService.getNotification(notification.getId());

        // then
        assertThat(result.getId()).isEqualTo(notification.getId());
        assertThat(result.getTitle()).isEqualTo(notification.getTitle());
        assertThat(result.getWriter()).isEqualTo(notification.getWriter());
        assertThat(result.getContent()).isEqualTo(notification.getContent());
        assertThat(result.getNotificationType()).isEqualTo(notification.getNotificationType());
    }

    @DisplayName("공지사항을 type 별로 불러온다.")
    @MethodSource("typeInputAndExpect")
    @ParameterizedTest
    void read_notification_by_type(final String type, final List<NotificationResponse> expect) {
        // given
        final Notification notification1 = new Notification("공지1", "작성자1", "공지 내용1", "all");
        final Notification notification2 = new Notification("공지2", "작성자2", "공지 내용2", "festival");
        final Notification notification3 = new Notification("공지3", "작성자3", "공지 내용3", "festival");
        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
        notificationRepository.save(notification3);


        // when
        final List<NotificationResponse> result = notificationService.getNotificationsByType(type);

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "modifiedAt", "images")
                .isEqualTo(expect);
    }

    private static Stream<Arguments> typeInputAndExpect() {
        return Stream.of(
                Arguments.of("all",
                        List.of(new NotificationResponse(1L,
                                "공지1",
                                "작성자1",
                                "공지 내용1",
                                NotificationType.ALL,
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                Collections.emptyList()))),
                Arguments.of("festival",
                        List.of(new NotificationResponse(2L,
                                        "공지2",
                                        "작성자2",
                                        "공지 내용2",
                                        NotificationType.FESTIVAL,
                                        LocalDateTime.now(),
                                        LocalDateTime.now(),
                                        Collections.emptyList()),
                                new NotificationResponse(3L,
                                        "공지3",
                                        "작성자3",
                                        "공지 내용3",
                                        NotificationType.FESTIVAL,
                                        LocalDateTime.now(),
                                        LocalDateTime.now(),
                                        Collections.emptyList()))),
                Arguments.of(null,
                        List.of(new NotificationResponse(1L,
                                        "공지1",
                                        "작성자1",
                                        "공지 내용1",
                                        NotificationType.ALL,
                                        LocalDateTime.now(),
                                        LocalDateTime.now(),
                                        Collections.emptyList()),
                                new NotificationResponse(2L,
                                        "공지2",
                                        "작성자2",
                                        "공지 내용2",
                                        NotificationType.FESTIVAL,
                                        LocalDateTime.now(),
                                        LocalDateTime.now(),
                                        Collections.emptyList()),
                                new NotificationResponse(3L,
                                        "공지3",
                                        "작성자3",
                                        "공지 내용3",
                                        NotificationType.FESTIVAL,
                                        LocalDateTime.now(),
                                        LocalDateTime.now(),
                                        Collections.emptyList())))
        );
    }

    @DisplayName("공지사항을 업데이트 한다.")
    @Test
    void update_notification() {
        // given
        final Notification notification = new Notification("공지1", "작성자1", "공지 내용1", "all");
        notificationRepository.save(notification);

        final NotificationRequest updateRequest = new NotificationRequest("공지 수정", "작성자", "공지 내용 수정", "all");

        // when
        final Notification result = notificationService.updateNotification(notification.getId(), updateRequest);

        // then
        assertThat(result.getId()).isEqualTo(notification.getId());
        assertThat(result.getTitle()).isEqualTo(updateRequest.getTitle());
        assertThat(result.getWriter()).isEqualTo(updateRequest.getWriter());
        assertThat(result.getContent()).isEqualTo(updateRequest.getContent());
        assertThat(result.getNotificationType()).isEqualTo(NotificationType.valueOf(updateRequest.getNotificationType().toUpperCase()));
    }

    @DisplayName("공지를 삭제한다.")
    @Test
    void delete_notification() {
        // given
        final Notification notification = new Notification("공지1", "작성자1", "공지 내용1", "all");
        notificationRepository.save(notification);

        // when
        notificationService.deleteNotification(notification.getId());
        final Optional<Notification> result = notificationRepository.findById(notification.getId());

        // then
        assertThat(result).isEmpty();
    }
}
