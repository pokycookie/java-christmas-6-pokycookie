package christmas.domain;

import christmas.menu.MenuType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BillTest {
    private static Bill testBill;

    @BeforeEach
    void billSetup() {
        testBill = Bill.from(Date.from(17));

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
}
