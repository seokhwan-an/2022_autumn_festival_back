package likelion.festival.domain.comment;

import likelion.festival.domain.BaseEntity;
import likelion.festival.domain.booth.Booth;
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

    @Embedded
    private Writer writer;

    @Embedded
    private Password password;

    @Embedded
    private Content content;

    @Embedded
    private Active active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    public Comment(final String writer,
                   final String password,
                   final String content,
                   final Booth booth
    ) {
        this.id = null;
        this.writer = new Writer(writer);
        this.password = new Password(password);
        this.content = new Content(content);
        this.active = new Active(true);
        this.booth = booth;
    }

    public boolean isSamePassword(final String inputPassword) {
        return this.password.isSamePassword(inputPassword);
    }

    public void delete() {
        active.delete();
    }

    public String getWriter() {
        return this.writer.getValue();
    }

    public String getContent() {
        return this.content.getValue();
    }

    public boolean getActive() {
        return this.active.getValue();
    }
}
