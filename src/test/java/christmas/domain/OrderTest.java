package christmas.domain;

import christmas.menu.Menu;
import christmas.menu.MenuType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {
    @Test
    void checkOrderPrice() {
        assertThat(Order.create(Menu.SEAFOOD_PASTA, 2).getPrice())
                .isEqualTo(70000);
    }

    @Test
    void checkOrderType() {
        assertThat(Order.create(Menu.ICE_CREAM, 3).getTypeCount(MenuType.DESSERT))
                .isEqualTo(3);
    }
}
