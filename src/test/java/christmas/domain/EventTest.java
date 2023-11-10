package christmas.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {
    @Test
    void checkEventName() {
        assertThat(Event.WEEKEND.getEventName()).isEqualTo("주말 할인");
    }

    @Test
    void checkIsDiscount() {
        assertThat(Event.CHAMPAGNE.isDiscount()).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"17,true", "20,false", "25,true", "29,false"})
    void checkSpecialCondition(int date, boolean answer) {
        Bill testBill = Bill.from(Date.from(date));
        assertThat(Event.SPECIAL.checkCondition(testBill)).isEqualTo(answer);
    }

    @ParameterizedTest
    @CsvSource(value = {"17,true", "20,true", "16,false", "29,false"})
    void checkWeekdayCondition(int date, boolean answer) {
        Bill testBill = Bill.from(Date.from(date));
        assertThat(Event.WEEKDAY.checkCondition(testBill)).isEqualTo(answer);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,1000", "3,1200", "25,3400"})
    void checkBenefit(int date, int answer) {
        Bill testBill = Bill.from(Date.from(date));
        assertThat(Event.CHRISTMAS.getBenefit(testBill)).isEqualTo(answer);
    }
}
