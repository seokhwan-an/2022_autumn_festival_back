package likelion.festival.domain.booth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TitleTest {

    @DisplayName("부스 제목은 null이나 빈 값이 될 수 없습니다.")
    @NullSource
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void fail_create_title(final String title) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Title(title))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("부스 제목의 길이가 100을 넘을 경우 예외를 발생시킨다.")
    @Test
    void fail_create_title_lengthOver100() {
        // given
        final String title = "A".repeat(101);

        // when
        // then
        assertThatThrownBy(() -> new Title(title))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
