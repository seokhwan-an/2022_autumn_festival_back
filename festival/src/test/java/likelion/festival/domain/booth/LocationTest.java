package likelion.festival.domain.booth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class LocationTest {

    @DisplayName("부스 위치는 null이나 빈 값이 될 수 없습니다.")
    @NullSource
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void fail_create_location(final String location) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Location(location))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("부스 위치의 길이가 30을 넘을 경우 예외를 발생시킨다.")
    @Test
    void fail_create_location_lengthOver30() {
        // given
        final String location = "A".repeat(31);

        // when
        // then
        assertThatThrownBy(() -> new Location(location))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
