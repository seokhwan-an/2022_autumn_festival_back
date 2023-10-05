package likelion.festival.domain.comment;

import likelion.festival.domain.BaseEntity;
import likelion.festival.domain.booth.Booth;
import likelion.festival.security.Encrypt;
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

    public Comment(final String writer,
                   final String password,
                   final String content,
                   final Booth booth
    ) {
        this.id = null;
        this.writer = writer;
        this.password = password;
        this.content = content;
        this.booth = booth;
    }

    public boolean isCorrectPassword(final String inputPassword) {
        return password.equals(Encrypt.getEncrypt(inputPassword));
    }

    public void delete() {
        this.active = false;
    }
}
