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
public class Introduction {

    private static final int MAX_INTRODUCTION_LENGTH = 100;

    @Column(name = "introduction", length = 100, nullable = false)
    private String value;

    public Introduction(final String value) {
        validateIntroduction(value);
        this.value = value;
    }

    private void validateIntroduction(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("한줄 소개글은 null이나 빈 값이 될 수 없습니다.");
        }
        if (value.length() > MAX_INTRODUCTION_LENGTH) {
            throw new IllegalArgumentException("한줄 소개글은 100자를 넘길 수 없습니다.");
        }
    }
}
