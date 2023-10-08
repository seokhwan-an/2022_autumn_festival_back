package likelion.festival.domain.menu;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Price {

    private static final int MIN_PRICE = 0;
    private static final int MAX_PRICE = 50_000;

    @Column(name = "price", nullable = false)
    private long value;

    public Price(long value) {
        validatePrice(value);
        this.value = value;
    }

    public void validatePrice(final long value) {
        if (value < MIN_PRICE || value > MAX_PRICE) {
            throw new IllegalArgumentException(String.format("메뉴 가격은 %d보다 크고 %d보다 작아야 합니다", MIN_PRICE, MAX_PRICE));
        }
    }
}
