package likelion.festival.domain.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PriceTest {

    @DisplayName("메뉴 가격이 0보다 작거나 50,000보다 큰 경우에는 예외를 발생시킨다.")
    @ValueSource(longs = {-1, 50001})
    @ParameterizedTest
    void fail_create_price_under_0_or_over_50000(final int price) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
