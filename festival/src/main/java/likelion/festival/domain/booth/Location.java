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
public class Location {

    private static final int MAX_LOCATION_LENGTH = 30;

    @Column(name = "location", length = 30, nullable = false)
    private String value;

    public Location(final String value) {
        validateLocation(value);
        this.value = value;
    }

    private void validateLocation(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("위치는 null이나 빈 값이 될 수 없습니다.");
        }
        if (value.length() > MAX_LOCATION_LENGTH) {
            throw new IllegalArgumentException("위치는 30자를 넘길 수 없습니다.");
        }
    }
}
