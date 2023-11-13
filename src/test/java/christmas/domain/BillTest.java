package christmas.domain;

import christmas.exception.ErrorMessage;
import christmas.menu.MenuType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BillTest {
    private static Bill testBill;

    @BeforeEach
    void billSetup() {
        testBill = Bill.from(17);

        testBill.add(Order.create("시저샐러드", 1)); // 8,000원 x 1, APPETIZER
        testBill.add(Order.create("해산물파스타", 1)); // 35,000원 x 1, MAIN
        testBill.add(Order.create("바비큐립", 1)); // 54,000원 x 1, MAIN
        testBill.add(Order.create("아이스크림", 2)); // 5,000원 x 2, DESSERT
        testBill.add(Order.create("제로콜라", 2)); // 3,000원 x 2, DRINK
    }

    @Test
    void checkTotalPrice() {
        assertThat(testBill.getTotalPrice()).isEqualTo(113000);
    }

    @Test
    void checkTypeCount() {
        assertThat(testBill.getTypeCount(MenuType.DRINK)).isEqualTo(2);
    }

    @DisplayName("중복되는 메뉴를 추가 시 예외 발생")
    @Test
    void checkDuplicatedMenu() {
        Bill testBill = Bill.from(25);

        assertThatThrownBy(() -> {
            testBill.add(Order.create("크리스마스파스타", 2));
            testBill.add(Order.create("크리스마스파스타", 5));
        }).isInstanceOf(IllegalArgumentException.class).hasMessage(ErrorMessage.WRONG_ORDER.getMessage());
    }

    @DisplayName("메뉴가 20개를 초과하는 경우 예외 발생")
    @Test
    void checkOverOrder() {
        Bill testBill = Bill.from(25);

        assertThatThrownBy(() -> {
            testBill.add(Order.create("해산물파스타", 5));
            testBill.add(Order.create("크리스마스파스타", 7));
            testBill.add(Order.create("제로콜라", 15));
        }).isInstanceOf(IllegalArgumentException.class).hasMessage(ErrorMessage.WRONG_ORDER.getMessage());
    }
}
