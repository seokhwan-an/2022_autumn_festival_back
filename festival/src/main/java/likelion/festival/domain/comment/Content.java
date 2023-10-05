package likelion.festival.domain.comment;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Content {

    private static final int MAX_CONTENT_LENGTH = 100;

    @Column(name = "content", length = 100, nullable = false)
    private String value;

    public Content(final String value) {
        validateContent(value);
        this.value = value;
    }

    private void validateContent(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("댓글 내용는 null이나 빈 값이 될 수 없습니다.");
        }
        if (value.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("댓글 내용는 10000자를 넘길 수 없습니다.");
        }
    }
}
