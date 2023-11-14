package christmas.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class BadgeTest {
    @DisplayName("getBadgeNameWithBenefitPrice 동작 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "0, 없음", "2500, 없음", "5000, 별", "9000, 별", "10000, 트리", "17000, 트리", "20000, 산타", "50000, 산타"
    })
    void checkBadgeNameWithBenefitPrice(int price, String answer) {
        assertThat(Badge.getBadgeNameWithBenefitPrice(price)).isEqualTo(answer);
    }
}
