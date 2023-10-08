package likelion.festival.domain.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class NameTest {

    @DisplayName("메뉴 이름은 null이나 빈 값이 될 수 없습니다.")
    @NullSource
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void fail_create_menuName(final String name) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 이름의 길이가 10을 넘을 경우 예외를 발생시킨다.")
    @Test
    void fail_create_name_lengthOver15() {
        // given
        final String name = "A".repeat(16);

        // when
        // then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
