package christmas.domain;

import christmas.menu.MenuType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {
    @Test
    void checkOrderPrice() {
        assertThat(Order.create("해산물파스타", 2).getPrice())
                .isEqualTo(70000);
    }

    @Test
    void checkOrderType() {
        assertThat(Order.create("아이스크림", 3).getTypeCount(MenuType.DESSERT))
                .isEqualTo(3);
    }
}
