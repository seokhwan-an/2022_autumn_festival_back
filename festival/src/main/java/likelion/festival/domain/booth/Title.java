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
public class Title {

    private static final int MAX_TITLE_LENGTH = 100;

    @Column(name = "title", length = 100, nullable = false)
    private String value;

    public Title(final String value) {
        validateTitle(value);
        this.value = value;
    }

    private void validateTitle(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("제목은 null이나 빈 값이 될 수 없습니다.");
        }
        if (value.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("제목은 100자를 넘길 수 없습니다.");
        }
    }
}
