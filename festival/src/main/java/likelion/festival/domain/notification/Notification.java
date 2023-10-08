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

    @Embedded
    private Title title;

    @Embedded
    private Writer writer;

    @Embedded
    private Content content;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @OneToMany(mappedBy = "notification")
    private List<Image> images = new ArrayList<>();

    public Notification(final String title, final String writer, final String content, final String type) {
        this.id = null;
        this.title = new Title(title);
        this.writer = new Writer(writer);
        this.content = new Content(content);
        this.notificationType = NotificationType.valueOf(type.toUpperCase());
    }

    public void update(final String title, final String writer, final String content, final String type) {
        this.title = new Title(title);
        this.writer = new Writer(writer);
        this.content = new Content(content);
        this.notificationType = NotificationType.valueOf(type.toUpperCase());
    }

    public boolean isSameType(final String type) {
        return notificationType.name().equalsIgnoreCase(type);
    }

    public String getTitle() {
        return this.title.getValue();
    }

    public String getWriter() {
        return this.writer.getValue();
    }

    public String getContent() {
        return this.content.getValue();
    }
}
