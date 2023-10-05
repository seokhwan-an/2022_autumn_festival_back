package likelion.festival.domain.booth;

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

    private static final int MAX_CONTENT_LENGTH = 10000;

    @Column(name = "content", length = 10000, nullable = false)
    private String value;

    public Content(final String value) {
        validateContent(value);
        this.value = value;
    }

    private void validateContent(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("상세정보는 null이나 빈 값이 될 수 없습니다.");
        }
        if (value.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("상세정보는 10000자를 넘길 수 없습니다.");
        }
    }
}
