package likelion.festival.domain.menu;

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
public class Name {

    private static final int MAX_NAME_LENGTH = 15;

    @Column(name = "name", length = 15, nullable = false)
    private String value;

    public Name(final String value) {
        validateName(value);
        this.value = value;
    }

    private void validateName(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("메뉴 이름는 null이나 빈 값이 될 수 없습니다.");
        }
        if (value.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("메뉴 이름는 15자를 넘길 수 없습니다.");
        }
    }
}
