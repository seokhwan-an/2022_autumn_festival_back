package likelion.festival.domain.like;

import likelion.festival.domain.booth.Booth;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "likes")
@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cookieKey", nullable = false)
    private String cookieKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    public Likes(final String cookieKey, final Booth booth) {
        this.id = null;
        this.cookieKey = cookieKey;
        this.booth = booth;
    }
}
