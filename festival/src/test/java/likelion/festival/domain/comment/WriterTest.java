package likelion.festival.domain.comment;

import likelion.festival.domain.booth.Location;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class WriterTest {

    @DisplayName("작성자는 null이나 빈 값이 될 수 없습니다.")
    @NullSource
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void fail_create_writer(final String writer) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Writer(writer))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("작성자의 길이가 10을 넘을 경우 예외를 발생시킨다.")
    @Test
    void fail_create_location_lengthOver10() {
        // given
        final String writer = "A".repeat(11);

        // when
        // then
        assertThatThrownBy(() -> new Writer(writer))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
