package likelion.festival.domain.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ContentTest {

    @DisplayName("댓글 내용은 null이나 빈 값이 될 수 없습니다.")
    @NullSource
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void fail_create_content(final String content) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Content(content))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("댓글 내용의 길이가 100을 넘을 경우 예외를 발생시킨다.")
    @Test
    void fail_create_content_lengthOver100() {
        // given
        final String content = "A".repeat(101);

        // when
        // then
        assertThatThrownBy(() -> new Content(content))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
