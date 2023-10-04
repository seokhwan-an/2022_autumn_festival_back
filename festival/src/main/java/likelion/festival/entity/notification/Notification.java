package likelion.festival.entity.notification;

import likelion.festival.entity.BaseEntity;
import likelion.festival.entity.Image;
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
}
