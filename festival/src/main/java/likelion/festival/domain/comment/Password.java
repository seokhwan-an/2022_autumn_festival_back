package likelion.festival.domain.comment;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Password {

    private static final int MAX_PASSWORD_LENGTH = 10;
    private static final Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d).+$");

    @Column(name = "password", length = 100, nullable = false)
    private String value;

    public Password(final String value) {
        validatePassword(value);
        this.value = value;
    }

    private void validatePassword(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 null이나 빈 값이 될 수 없습니다.");
        }
        if (value.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("비밀번호는 10000자를 넘길 수 없습니다.");
        }
        final Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("비밀번호는 최소하나의 영문자와 숫자로 구성되어야 합니다.");
        }
    }
}
