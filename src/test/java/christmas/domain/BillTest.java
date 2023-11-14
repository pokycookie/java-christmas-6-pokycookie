package christmas.domain;

import christmas.dto.OrderDTO;
import christmas.exception.ErrorMessage;
import christmas.menu.MenuType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BillTest {
    @DisplayName("잘못된 date 값 사용 시 예외 발생")
    @ParameterizedTest
    @ValueSource(ints = {-5, 0, 32, 75})
    void checkCreateFromWrongDate(int date) {
        assertThatThrownBy(() -> Bill.from(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.WRONG_DATE.getMessage());
    }

    @DisplayName("add 동작 확인")
    @Test
    void checkAddMethod() {
        Bill bill = Bill.from(1)
                .add(Order.create("시저샐러드", 3))
                .add(Order.create("해산물파스타", 1))
                .add(Order.create("제로콜라", 2));

        List<OrderDTO> answer = List.of(
                new OrderDTO("시저샐러드", 3),
                new OrderDTO("해산물파스타", 1),
                new OrderDTO("제로콜라", 2)
        );

        assertThat(bill.getAllOrders()).isEqualTo(answer);
    }

    @DisplayName("중복된 메뉴를 입력시 예외 발생")
    @Test
    void checkDuplicatedMenu() {
        assertThatThrownBy(() -> Bill.from(1)
                .add(Order.create("크리스마스파스타", 2))
                .add(Order.create("바비큐립", 1))
                .add(Order.create("크리스마스파스타", 3))
        ).isInstanceOf(IllegalArgumentException.class).hasMessage(ErrorMessage.WRONG_ORDER.getMessage());
    }

    @DisplayName("메뉴가 20개를 초과하는 경우 예외 발생")
    @ParameterizedTest
    @MethodSource("overedOrder")
    void checkOverOrder(List<Order> orders) {
        Bill bill = Bill.from(1);

        assertThatThrownBy(() -> orders.forEach(bill::add))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.WRONG_ORDER.getMessage());
    }

    static Stream<Arguments> overedOrder() {
        return Stream.of(
                Arguments.of(List.of(
                        Order.create("해산물파스타", 7),
                        Order.create("제로콜라", 15)
                )),
                Arguments.of(List.of(
                        Order.create("아이스크림", 25)
                ))
        );
    }

    @DisplayName("clearOrder 동작 확인")
    @Test
    void checkClearOrder() {
        Bill bill = Bill.from(1)
                .add(Order.create("시저샐러드", 3))
                .add(Order.create("해산물파스타", 1))
                .add(Order.create("제로콜라", 2));
        bill.clearOrder();
        assertThat(bill.getAllOrders()).isEqualTo(List.of());
    }

    @DisplayName("getTotalPrice 동작 확인")
    @Test
    void checkTotalPrice() {
        Bill bill = Bill.from(1)
                .add(Order.create("타파스", 2))
                .add(Order.create("티본스테이크", 1))
                .add(Order.create("제로콜라", 2));
        assertThat(bill.getTotalPrice()).isEqualTo(72000);
    }

    @DisplayName("getTypeCount 동작 확인")
    @ParameterizedTest
    @MethodSource("typedBill")
    void checkTypeCount(Bill bill, MenuType type, int answer) {
        assertThat(bill.getTypeCount(type)).isEqualTo(answer);
    }

    static Stream<Arguments> typedBill() {
        return Stream.of(
                Arguments.of(
                        Bill.from(1)
                                .add(Order.create("양송이수프", 3))
                                .add(Order.create("티본스테이크", 1))
                                .add(Order.create("바비큐립", 2)),
                        MenuType.MAIN,
                        3
                ),
                Arguments.of(
                        Bill.from(1)
                                .add(Order.create("티본스테이크", 2))
                                .add(Order.create("초코케이크", 2))
                                .add(Order.create("아이스크림", 3))
                                .add(Order.create("제로콜라", 7)),
                        MenuType.DESSERT,
                        5
                )
        );
    }

    @DisplayName("getDateValue 동작 확인")
    @ParameterizedTest
    @ValueSource(ints = {1, 17, 25, 31})
    void checkDateValue(int date) {
        assertThat(Bill.from(date).getDateValue()).isEqualTo(date);
    }

    @DisplayName("음료만 주문한 경우 예외 발생")
    @Test
    void checkOnlyDrinkOrder() {
        assertThatThrownBy(() -> Bill.from(1)
                .add(Order.create("제로콜라", 3))
                .add(Order.create("레드와인", 1))
                .validateOnlyDrink()
        ).isInstanceOf(IllegalArgumentException.class).hasMessage(ErrorMessage.WRONG_ORDER.getMessage());
    }
}
