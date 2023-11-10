package christmas.domain;

import christmas.menu.Menu;
import christmas.menu.MenuType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BillTest {
    private static Bill testBill;

    @BeforeEach
    void billSetup() {
        testBill = Bill.from(Date.from(17));

        testBill.add(Order.create(Menu.SALAD, 1)); // 8,000원 x 1, APPETIZER
        testBill.add(Order.create(Menu.SEAFOOD_PASTA, 1)); // 35,000원 x 1, MAIN
        testBill.add(Order.create(Menu.BARBECUE, 1)); // 54,000원 x 1, MAIN
        testBill.add(Order.create(Menu.ICE_CREAM, 2)); // 5,000원 x 2, DESSERT
        testBill.add(Order.create(Menu.COLA, 2)); // 3,000원 x 2, DRINK
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
