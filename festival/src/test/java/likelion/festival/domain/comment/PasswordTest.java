package likelion.festival.domain.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PasswordTest {

    @DisplayName("비밀번호는 null이나 빈 값이 될 수 없습니다.")
    @NullSource
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void fail_create_password(final String password) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("비밀번호의 길이가 10을 넘을 경우 예외를 발생시킨다.")
    @Test
    void fail_create_password_lengthOver10() {
        // given
        final String password = "A".repeat(11);

        // when
        // then
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("비밀번호에는 최소 1개의 영문자와 숫자가 없으면 예외를 발생합니다.")
    @ValueSource(strings = {"!@$@%", "asdfzvew", "12345", "비밀번호"})
    @ParameterizedTest
    void fail_create_password_wrong(final String password) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
