package likelion.festival.domain.notification;

import likelion.festival.domain.BaseEntity;
import likelion.festival.domain.image.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "notification")
@Entity
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "writer", length = 10, nullable = false)
    private String writer;

    @Column(name = "content", length = 10000, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @OneToMany(mappedBy = "notification")
    private List<Image> images = new ArrayList<>();

    public Notification(final String title, final String writer, final String content, final String type) {
        this.id = null;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.notificationType = NotificationType.valueOf(type.toUpperCase());
    }

    public void update(final String title, final String writer, final String content, final String type) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.notificationType = NotificationType.valueOf(type.toUpperCase());
    }

    public boolean isSameType(final String type) {
        return  notificationType.name().equalsIgnoreCase(type);
    }
}
