package christmas.domain;

import christmas.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

public class DateTest {
    @DisplayName("생성 시 validate 동작 확인")
    @ParameterizedTest
    @ValueSource(ints = {1, 25, 31})
    void checkValidator(int date) {
        assertThatCode(() -> Date.from(date)).doesNotThrowAnyException();
    }

    @DisplayName("잘못된 숫자로 생성 시 validate 예외 발생")
    @ParameterizedTest
    @ValueSource(ints = {-5, 0, 32, 75})
    void checkValidatorException(int date) {
        assertThatThrownBy(() -> Date.from(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.WRONG_DATE.getMessage());
    }

    @DisplayName("isWeekend 동작 확인")
    @ParameterizedTest
    @CsvSource(value = {"1, true", "8, true", "16, true", "17, false", "25, false", "28, false"})
    void checkIsWeekend(int date, boolean answer) {
        assertThat(Date.from(date).isWeekend()).isEqualTo(answer);
    }

    @DisplayName("isSpecialDay 동작 확인")
    @ParameterizedTest
    @CsvSource(value = {"3, true", "25, true", "31, true", "13, false", "22, false"})
    void checkIsSpecialDay(int date, boolean answer) {
        assertThat(Date.from(date).isSpecialDay()).isEqualTo(answer);
    }

    @DisplayName("isNotPassedChristmas 동작 확인")
    @ParameterizedTest
    @CsvSource(value = {"1, true", "14, true", "25, true", "26, false", "31, false"})
    void checkIsNotPassedChristmas(int date, boolean answer) {
        assertThat(Date.from(date).isNotPassedChristmas()).isEqualTo(answer);
    }

    @DisplayName("timePassedSinceFirstDay 동작 확인")
    @ParameterizedTest
    @CsvSource(value = {"1, 0", "18, 17", "25, 24"})
    void checkTimePassedSinceFirstDay(int date, int answer) {
        assertThat(Date.from(date).timePassedSinceFirstDay()).isEqualTo(answer);
    }
}
