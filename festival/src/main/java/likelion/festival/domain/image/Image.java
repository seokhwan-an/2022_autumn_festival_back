package likelion.festival.domain.image;

import likelion.festival.domain.booth.Booth;
import likelion.festival.domain.notification.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "image")
@Entity
public class Image {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String originFileName;

    private String serverFileName;

    private String storedFilePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    public Image(final String originFileName,
                 final String serverFileName,
                 final String storedFilePath,
                 final Booth booth
    ) {
        this.id = null;
        this.originFileName = originFileName;
        this.serverFileName = serverFileName;
        this.storedFilePath = storedFilePath;
        this.booth = booth;
    }

    public Image(final String originFileName,
                 final String serverFileName,
                 final String storedFilePath,
                 final Notification notification
    ) {
        this.id = null;
        this.originFileName = originFileName;
        this.serverFileName = serverFileName;
        this.storedFilePath = storedFilePath;
        this.notification = notification;
    }
}
