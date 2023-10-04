package likelion.festival.entity.booth;

import likelion.festival.entity.Image;
import likelion.festival.entity.Likes;
import likelion.festival.entity.comment.Comment;
import likelion.festival.entity.menu.Menu;
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
}
