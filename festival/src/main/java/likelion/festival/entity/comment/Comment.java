package likelion.festival.entity.comment;

import likelion.festival.entity.BaseEntity;
import likelion.festival.entity.booth.Booth;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "comment")
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "writer", length = 10, nullable = false)
    private String writer;

    @Column(name = "password", length = 10, nullable = false)
    private String password;

    @Column(name = "content", length = 100, nullable = false)
    private String content;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;
}
