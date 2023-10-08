package likelion.festival.domain.notification;

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
public class Writer {

    private static final int MAX_WRITER_LENGTH = 10;

    @Column(name = "writer", length = 10, nullable = false)
    private String value;

    public Writer(final String value) {
        validateWriter(value);
        this.value = value;
    }

    private void validateWriter(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("작성자는 null이나 빈 값이 될 수 없습니다.");
        }
        if (value.length() > MAX_WRITER_LENGTH) {
            throw new IllegalArgumentException("작성자는 10자를 넘길 수 없습니다.");
        }
    }
}
