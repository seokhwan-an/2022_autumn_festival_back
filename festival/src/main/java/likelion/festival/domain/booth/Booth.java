package likelion.festival.domain.booth;

import likelion.festival.domain.comment.Comment;
import likelion.festival.domain.image.Image;
import likelion.festival.domain.like.Likes;
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

    @Embedded
    private Title title;

    @Embedded
    private Introduction introduction;

    @Embedded
    private Content content;

    @Embedded
    private Notice notice;

    @Enumerated(EnumType.STRING)
    private BoothType boothType;

    @Embedded
    private Location location;

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
        this.title = new Title(title);
        this.introduction = new Introduction(introduction);
        this.content = new Content(content);
        this.notice = new Notice(notice);
        this.boothType = boothType;
        this.location = new Location(location);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public boolean isActive(final LocalDate today) {
        return startAt.isBefore(today) && endAt.isAfter(today)
                || startAt.isEqual(today)
                || endAt.isEqual(today);
    }

    public String getTitle() {
        return this.title.getValue();
    }

    public String getIntroduction() {
        return this.introduction.getValue();
    }

    public String getContent() {
        return this.content.getValue();
    }

    public String getNotice() {
        return this.notice.getValue();
    }

    public String getLocation() {
        return this.location.getValue();
    }

    public int getLikeCount() {
        return likes.size();
    }
}
