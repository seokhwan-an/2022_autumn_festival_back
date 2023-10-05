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
public class Notice {

    private static final int MAX_NOTICE_LENGTH = 100;

    @Column(name = "notice", length = 100, nullable = false)
    private String value;

    public Notice(final String value) {
        validateNotice(value);
        this.value = value;
    }

    private void validateNotice(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("공지사항은 null이나 빈 값이 될 수 없습니다.");
        }
        if (value.length() > MAX_NOTICE_LENGTH) {
            throw new IllegalArgumentException("공지사항은 100자를 넘길 수 없습니다.");
        }
    }
}
