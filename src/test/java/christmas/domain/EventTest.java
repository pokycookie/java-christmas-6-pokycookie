package christmas.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {
    @DisplayName("getEventName 동작 확인")
    @Test
    void checkEventName() {
        assertThat(Event.WEEKEND.getEventName()).isEqualTo("주말 할인");
    }

    @DisplayName("isDiscount 동작 확인")
    @ParameterizedTest
    @MethodSource("checkDiscountTarget")
    void checkIsDiscount(Event event, boolean answer) {
        assertThat(event.isDiscount()).isEqualTo(answer);
    }

    static Stream<Arguments> checkDiscountTarget() {
        return Stream.of(
                Arguments.of(Event.CHRISTMAS, true),
                Arguments.of(Event.WEEKDAY, true),
                Arguments.of(Event.CHAMPAGNE, false)
        );
    }

    @DisplayName("증정 이벤트 동작 확인")
    @ParameterizedTest
    @MethodSource("giftEventTarget")
    void checkGiftEvent(Bill bill, boolean answer) {
        assertThat(Event.CHAMPAGNE.checkCondition(bill)).isEqualTo(answer);
    }

    static Stream<Arguments> giftEventTarget() {
        return Stream.of(
                Arguments.of(
                        Bill.from(1)
                                .add(Order.create("티본스테이크", 2))
                                .add(Order.create("아이스크림", 2)),
                        true
                ),
                Arguments.of(
                        Bill.from(1)
                                .add(Order.create("티본스테이크", 2))
                                .add(Order.create("시저샐러드", 5)),
                        true
                ),
                Arguments.of(
                        Bill.from(1)
                                .add(Order.create("바비큐립", 1))
                                .add(Order.create("양송이수프", 1)),
                        false
                )
        );
    }

    @DisplayName("크리스마스 디데이 할인 getBenefit 동작 확인")
    @ParameterizedTest
    @CsvSource(value = {"1, 1000", "11, 2000", "25, 3400"})
    void checkChristmasEventBenefit(int date, int answer) {
        Bill bill = Bill.from(date).add(Order.create("티본스테이크", 10));
        assertThat(Event.CHRISTMAS.getBenefit(bill)).isEqualTo(answer);
    }

    @DisplayName("평일 할인 getBenefit 동작 확인")
    @ParameterizedTest
    @MethodSource("WeekdayEventTarget")
    void checkWeekdayEventBenefit(Bill bill, int answer) {
        assertThat(Event.WEEKDAY.getBenefit(bill)).isEqualTo(answer);
    }

    static Stream<Arguments> WeekdayEventTarget() {
        return Stream.of(
                Arguments.of(
                        Bill.from(1)
                                .add(Order.create("양송이수프", 1))
                                .add(Order.create("바비큐립", 1)),
                        0
                ),
                Arguments.of(
                        Bill.from(1)
                                .add(Order.create("타파스", 1))
                                .add(Order.create("해산물파스타", 1))
                                .add(Order.create("초코케이크", 2)),
                        4046
                ),
                Arguments.of(
                        Bill.from(1)
                                .add(Order.create("바비큐립", 2))
                                .add(Order.create("초코케이크", 2))
                                .add(Order.create("아이스크림", 3)),
                        10115
                )
        );
    }

    @DisplayName("주말 할인 getBenefit 동작 확인")
    @ParameterizedTest
    @MethodSource("WeekendEventTarget")
    void checkWeekendEventBenefit(Bill bill, int answer) {
        assertThat(Event.WEEKEND.getBenefit(bill)).isEqualTo(answer);
    }

    static Stream<Arguments> WeekendEventTarget() {
        return Stream.of(
                Arguments.of(
                        Bill.from(1)
                                .add(Order.create("양송이수프", 2))
                                .add(Order.create("제로콜라", 2)),
                        0
                ),
                Arguments.of(
                        Bill.from(1)
                                .add(Order.create("타파스", 1))
                                .add(Order.create("티본스테이크", 1)),
                        2023
                ),
                Arguments.of(
                        Bill.from(1)
                                .add(Order.create("티본스테이크", 1))
                                .add(Order.create("바비큐립", 2))
                                .add(Order.create("해산물파스타", 3))
                                .add(Order.create("제로콜라", 6)),
                        12138
                )
        );
    }

    @DisplayName("특별 할인 getBenefit 동작 확인")
    @Test
    void checkSpecialEventBenefit() {
        Bill bill = Bill.from(1).add(Order.create("티본스테이크", 10));
        assertThat(Event.SPECIAL.getBenefit(bill)).isEqualTo(1000);
    }

    @DisplayName("증정 이벤트 getBenefit 동작 확인")
    @Test
    void checkGiftEventBenefit() {
        Bill bill = Bill.from(1).add(Order.create("티본스테이크", 10));
        assertThat(Event.CHAMPAGNE.getBenefit(bill)).isEqualTo(25000);
    }

    @DisplayName("총주문금액이 10000원 이상인 경우만 이벤트 적용 확인")
    @ParameterizedTest
    @MethodSource("BenefitWithTotalPriceTarget")
    void checkBenefitWithTotalPrice(Bill bill, Event event) {
        assertThat(event.checkCondition(bill)).isFalse();
    }

    static Stream<Arguments> BenefitWithTotalPriceTarget() {
        Order order = Order.create("양송이수프", 1);
        return Stream.of(
                Arguments.of(Bill.from(25).add(order), Event.CHRISTMAS),
                Arguments.of(Bill.from(19).add(order), Event.WEEKDAY),
                Arguments.of(Bill.from(22).add(order), Event.WEEKEND),
                Arguments.of(Bill.from(10).add(order), Event.SPECIAL)
        );
    }
}
