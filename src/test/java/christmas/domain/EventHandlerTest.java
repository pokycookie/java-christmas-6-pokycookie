package christmas.domain;

import christmas.dto.BenefitDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class EventHandlerTest {
    private static List<Bill> getSampleBill() {
        return List.of(
                Bill.from(1)
                        .add(Order.create("티본스테이크", 2))
                        .add(Order.create("아이스크림", 1)),
                Bill.from(3)
                        .add(Order.create("티본스테이크", 1))
                        .add(Order.create("아이스크림", 4)),
                Bill.from(13)
                        .add(Order.create("해산물파스타", 10))
                        .add(Order.create("초코케이크", 1))
                        .add(Order.create("아이스크림", 2)),
                Bill.from(25)
                        .add(Order.create("크리스마스파스타", 2))
                        .add(Order.create("샴페인", 2)),
                Bill.from(30)
                        .add(Order.create("시저샐러드", 5))
                        .add(Order.create("바비큐립", 2))
                        .add(Order.create("티본스테이크", 1))
                        .add(Order.create("레드와인", 2))
        );
    }

    @DisplayName("샴페인 증정 이벤트 확인")
    @Test
    void checkChampagneGift() {
        Bill bill = Bill.from(1).add(Order.create("티본스테이크", 20));
        assertThat(EventHandler.from(bill).hasChampagneGift()).isTrue();
    }

    @DisplayName("getTotalDiscountPrice 동작 확인")
    @ParameterizedTest
    @MethodSource("totalDiscountPriceTarget")
    void checkTotalDiscountPrice(Bill bill, int answer) {
        assertThat(EventHandler.from(bill).getTotalDiscountPrice()).isEqualTo(answer);
    }

    static Stream<Arguments> totalDiscountPriceTarget() {
        return Stream.of(
                Arguments.of(getSampleBill().get(0), 5046),
                Arguments.of(getSampleBill().get(1), 10292),
                Arguments.of(getSampleBill().get(2), 8269),
                Arguments.of(getSampleBill().get(3), 4400),
                Arguments.of(getSampleBill().get(4), 6069)
        );
    }

    @DisplayName("getTotalBenefitPrice 동작 확인")
    @ParameterizedTest
    @MethodSource("totalBenefitPriceTarget")
    void checkTotalBenefitPrice(Bill bill, int answer) {
        assertThat(EventHandler.from(bill).getTotalBenefitPrice()).isEqualTo(answer);
    }

    static Stream<Arguments> totalBenefitPriceTarget() {
        return Stream.of(
                Arguments.of(getSampleBill().get(0), 5046),
                Arguments.of(getSampleBill().get(1), 10292),
                Arguments.of(getSampleBill().get(2), 33269),
                Arguments.of(getSampleBill().get(3), 4400),
                Arguments.of(getSampleBill().get(4), 31069)
        );
    }

    @DisplayName("getAllBenefit 동작 확인")
    @ParameterizedTest
    @MethodSource("allBenefitTarget")
    void checkAllBenefit(Bill bill, List<BenefitDTO> answer) {
        assertThat(EventHandler.from(bill).getAllBenefit()).isEqualTo(answer);
    }

    static Stream<Arguments> allBenefitTarget() {
        return Stream.of(
                Arguments.of(getSampleBill().get(0), List.of(
                        new BenefitDTO("크리스마스 디데이 할인", 1000),
                        new BenefitDTO("주말 할인", 4046)
                )),
                Arguments.of(getSampleBill().get(1), List.of(
                        new BenefitDTO("크리스마스 디데이 할인", 1200),
                        new BenefitDTO("평일 할인", 8092),
                        new BenefitDTO("특별 할인", 1000)
                )),
                Arguments.of(getSampleBill().get(2), List.of(
                        new BenefitDTO("크리스마스 디데이 할인", 2200),
                        new BenefitDTO("평일 할인", 6069),
                        new BenefitDTO("증정 이벤트", 25000)
                )),
                Arguments.of(getSampleBill().get(3), List.of(
                        new BenefitDTO("크리스마스 디데이 할인", 3400),
                        new BenefitDTO("특별 할인", 1000)
                )),
                Arguments.of(getSampleBill().get(4), List.of(
                        new BenefitDTO("주말 할인", 6069),
                        new BenefitDTO("증정 이벤트", 25000)
                ))
        );
    }
}
