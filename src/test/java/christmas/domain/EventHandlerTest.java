package christmas.domain;

import christmas.menu.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EventHandlerTest {
    private static Bill testBill;

    private void billSetup(int date) {
        testBill = Bill.from(Date.from(date));

        testBill.add(Order.create(Menu.SALAD, 1)); // 8,000원 x 1, APPETIZER
        testBill.add(Order.create(Menu.SEAFOOD_PASTA, 1)); // 35,000원 x 1, MAIN
        testBill.add(Order.create(Menu.BARBECUE, 1)); // 54,000원 x 1, MAIN
        testBill.add(Order.create(Menu.SEAFOOD_PASTA, 2)); // 35,000원 x 2, MAIN
        testBill.add(Order.create(Menu.ICE_CREAM, 2)); // 5,000원 x 2, DESSERT
        testBill.add(Order.create(Menu.COLA, 2)); // 3,000원 x 2, DRINK
    }

    @DisplayName("샴페인 증정 이벤트 확인")
    @Test
    void checkChampagneGift() {
        billSetup(22);
        assertThat(EventHandler.from(testBill).hasChampagneGift()).isTrue();
    }

    @DisplayName("할인 금액 확인")
    @Test
    void checkTotalDiscountPrice() {
        billSetup(22);
        assertThat(EventHandler.from(testBill).getTotalDiscountPrice()).isEqualTo(11192);
    }

    @DisplayName("총혜택 금액 확인")
    @Test
    void checkTotalBenefitPrice() {
        billSetup(17);
        assertThat(EventHandler.from(testBill).getTotalBenefitPrice())
                .isEqualTo(2600 + 4046 + 1000 + 25000);
    }

    @DisplayName("혜택 내역 확인")
    @Test
    void checkAllBenefit() {
        billSetup(22);
        assertThat(EventHandler.from(testBill).getAllBenefit())
                .isEqualTo(List.of(
                        new Benefit("크리스마스 디데이 할인", 3100),
                        new Benefit("주말 할인", 8092),
                        new Benefit("증정 이벤트", 25000)
                ));
    }
}
