package christmas.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class DateTest {
    @ParameterizedTest
    @CsvSource(value = {"1, true", "2, true", "3, false", "20, false", "25, false"})
    void checkIsWeekend(int date, boolean answer) {
        assertThat(Date.from(date).isWeekend()).isEqualTo(answer);
    }

    @ParameterizedTest
    @CsvSource(value = {"3, true", "22, false", "25, true", "30, false", "13, false"})
    void checkIsSpecialDay(int date, boolean answer) {
        assertThat(Date.from(date).isSpecialDay()).isEqualTo(answer);
    }
}
