package likelion.festival.controller.notification;

import likelion.festival.dto.notification.NotificationRequest;
import likelion.festival.dto.notification.NotificationResponse;
import likelion.festival.domain.notification.Notification;
import likelion.festival.service.ImageService;
import likelion.festival.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "api/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;
    private final ImageService imageService;

    @GetMapping("{id}")
    public NotificationResponse readNotification(@PathVariable final Long id) {
        return notificationService.getNotification(id);
    }

    @GetMapping
    public ResponseEntity readNotificationAll(@RequestParam(required = false) final String notificationType) {
        return ResponseEntity.ok(notificationService.getNotificationsByType(notificationType));
    }

    @PostMapping
    public Integer createNotification(@RequestPart(value = "imgList", required = false) List<MultipartFile> imgList,
                                      @RequestBody final NotificationRequest request) {
        Notification notification = notificationService.createNotification(request);
        if (imgList == null) {
            return HttpStatus.OK.value();
        }
        imageService.saveNotificationImage(imgList, notification);
        return HttpStatus.OK.value();
    }

    @DeleteMapping("{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "Ok";
    }

    @PutMapping("{id}")

    public ResponseEntity<Notification> updateNotification(@PathVariable final Long id,
                                                           @RequestBody final NotificationRequest request) {
        return ResponseEntity.ok(notificationService.updateNotification(id, request));
    }
}
