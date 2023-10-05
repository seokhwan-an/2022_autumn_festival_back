package likelion.festival.domain.booth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class NoticeTest {

    @DisplayName("부스 공지사항은 null이나 빈 값이 될 수 없습니다.")
    @NullSource
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void fail_create_notice(final String notice) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Notice(notice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("부스 공지사항의 길이가 100을 넘을 경우 예외를 발생시킨다.")
    @Test
    void fail_create_notice_lengthOver100() {
        // given
        final String notice = "A".repeat(101);

        // when
        // then
        assertThatThrownBy(() -> new Notice(notice))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
