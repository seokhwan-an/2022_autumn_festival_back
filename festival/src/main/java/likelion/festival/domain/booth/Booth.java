package likelion.festival.domain.booth;

import likelion.festival.domain.image.Image;
import likelion.festival.domain.like.Likes;
import likelion.festival.domain.comment.Comment;
import likelion.festival.domain.menu.Menu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "booth")
@Entity
public class Booth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "introdction", length = 100, nullable = false)
    private String introduction;

    @Column(name = "content", length = 10000, nullable = false)
    private String content;

    @Column(name = "notice", length = 100, nullable = false)
    private String notice;

    @Enumerated(EnumType.STRING)
    private BoothType boothType;

    @Column(name = "location", length = 100, nullable = false)
    private String location;

    private LocalDate startAt;

    private LocalDate endAt;

    @OneToMany(mappedBy = "booth")
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "booth")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "booth")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "booth")
    private List<Image> images = new ArrayList<>();

    public Booth(final String title,
                 final String introduction,
                 final String content,
                 final String notice,
                 final BoothType boothType,
                 final String location,
                 final LocalDate startAt,
                 final LocalDate endAt
    ) {
        this.id = null;
        this.title = title;
        this.introduction = introduction;
        this.content = content;
        this.notice = notice;
        this.boothType = boothType;
        this.location = location;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public boolean isActive(final LocalDate today) {
        return startAt.isBefore(today) && endAt.isAfter(today)
                || startAt.isEqual(today)
                || endAt.isEqual(today);
    }

    public int getLikeCount() {
        return likes.size();
    }
}
