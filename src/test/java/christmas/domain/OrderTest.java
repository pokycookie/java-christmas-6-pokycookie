package christmas.domain;

import christmas.exception.ErrorMessage;
import christmas.menu.MenuType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderTest {
    @DisplayName("잘못된 메뉴 이름 사용시 예외 발생")
    @Test
    void checkWrongNameOrder() {
        assertThatThrownBy(() -> Order.create("복숭아아이스티", 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.WRONG_ORDER.getMessage());
    }

    @DisplayName("잘못된 count 사용시 예외 발생")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void checkWrongCountOrder(int count) {
        assertThatThrownBy(() -> Order.create("양송이수프", count))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.WRONG_ORDER.getMessage());
    }

    @DisplayName("getPrice 동작 확인")
    @ParameterizedTest
    @CsvSource(value = {"타파스, 2, 11000", "바비큐립, 3, 162000", "아이스크림, 5, 25000"})
    void checkOrderPrice(String menuName, int count, int answer) {
        assertThat(Order.create(menuName, count).getPrice())
                .isEqualTo(answer);
    }

    @DisplayName("isSameMenu 동작 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "타파스, 타파스, true",
            "레드와인, 레드와인, true",
            "티본스테이크, 아이스크림, false",
            "초코케이크, 바비큐립, false",
    })
    void checkIsSameMenu(String menuName1, String menuName2, boolean answer) {
        assertThat(Order.create(menuName1, 1)
                .isSameMenu(Order.create(menuName2, 1)))
                .isEqualTo(answer);
    }

    @DisplayName("accumulateCount 동작 확인")
    @ParameterizedTest
    @MethodSource("accumulateCountTarget")
    void checkAccumulateCount(List<Order> orders, int answer) {
        assertThat(Order.accumulateCount(orders)).isEqualTo(answer);
    }

    static Stream<Arguments> accumulateCountTarget() {
        return Stream.of(
                Arguments.of(List.of(
                      Order.create("티본스테이크", 5)
                ), 5),
                Arguments.of(List.of(
                        Order.create("양송이수프", 3),
                        Order.create("바비큐립", 1),
                        Order.create("아이스크림", 3)
                ), 7)
        );
    }

    @DisplayName("accumulateCount type 사용하여 동작 확인")
    @ParameterizedTest
    @MethodSource("accumulateCountWithTypeTarget")
    void checkAccumulateCountWithType(List<Order> orders, MenuType type, int answer) {
        assertThat(Order.accumulateCount(orders, type)).isEqualTo(answer);
    }

    static Stream<Arguments> accumulateCountWithTypeTarget() {
        return Stream.of(
                Arguments.of(List.of(
                        Order.create("티본스테이크", 2),
                        Order.create("제로콜라", 4)
                ), MenuType.DESSERT, 0),
                Arguments.of(List.of(
                        Order.create("양송이수프", 3),
                        Order.create("바비큐립", 1),
                        Order.create("해산물파스타", 2),
                        Order.create("아이스크림", 5)
                ), MenuType.MAIN, 3)
        );
    }
}
