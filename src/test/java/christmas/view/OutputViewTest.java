package christmas.view;

import christmas.dto.BenefitDTO;
import christmas.dto.OrderDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputViewTest {
    private static ByteArrayOutputStream console;

    @BeforeEach
    void streamSetup() {
        console = new ByteArrayOutputStream();
        System.setOut(new PrintStream(console));
    }

    @AfterEach
    void streamRestore() {
        System.setOut(System.out);
    }

    @Test
    void checkPrintHelloMessage() {
        OutputView.printHelloMessage();
        assertThat(console.toString())
                .contains("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }

    @Test
    void checkPrintEventPreviewTitle() {
        OutputView.printEventPreviewTitle(25);
        assertThat(console.toString())
                .contains("12월 25일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!");
    }

    @Test
    void checkPrintOrder() {
        OutputView.printOrder(List.of(
                new OrderDTO("티본스테이크", 1),
                new OrderDTO("바비큐립", 2),
                new OrderDTO("초코케이크", 2),
                new OrderDTO("제로콜라", 1)
        ));

        String answer = String.join("\n",
                "<주문 메뉴>",
                "티본스테이크 1개",
                "바비큐립 2개",
                "초코케이크 2개",
                "제로콜라 1개"
        );

        assertThat(console.toString()).isEqualToIgnoringNewLines(answer);
    }

    @ParameterizedTest
    @CsvSource(value = {"1250000, '1,250,000원'", "0, '0원'", "142000, '142,000원'"})
    void checkPrintTotalPrice(int price, String result) {
        OutputView.printTotalPrice(price);
        String answer = String.join("\n", "<할인 전 총주문 금액>", result);

        assertThat(console.toString()).isEqualToIgnoringNewLines(answer);
    }

    @ParameterizedTest
    @CsvSource(value = {"true, 샴페인 1개", "false, 없음"})
    void checkPrintGift(boolean gift, String result) {
        OutputView.printGift(gift);
        String answer = String.join("\n", "<증정 메뉴>", result);

        assertThat(console.toString()).isEqualToIgnoringNewLines(answer);
    }

    @Test
    void checkPrintAllBenefit() {
        List<BenefitDTO> benefit = List.of(
                new BenefitDTO("크리스마스 디데이 할인", 1200),
                new BenefitDTO("평일 할인", 4046),
                new BenefitDTO("특별 할인", 1000),
                new BenefitDTO("증정 이벤트", 25000)
        );

        OutputView.printAllBenefit(benefit);
        String answer = String.join("\n",
                "<혜택 내역>",
                "크리스마스 디데이 할인: -1,200원",
                "평일 할인: -4,046원",
                "특별 할인: -1,000원",
                "증정 이벤트: -25,000원"
        );

        assertThat(console.toString()).isEqualToIgnoringNewLines(answer);
    }

    @ParameterizedTest
    @CsvSource(value = {"31246, '-31,246원'", "0, '0원'"})
    void checkPrintBenefitPrice(int price, String result) {
        OutputView.printBenefitPrice(price);
        String answer = String.join("\n", "<총혜택 금액>", result);

        assertThat(console.toString()).isEqualToIgnoringNewLines(answer);
    }

    @ParameterizedTest
    @CsvSource(value = {"135754, '135,754원'", "0, '0원'"})
    void checkPrintAfterDiscountPrice(int price, String result) {
        OutputView.printAfterDiscountPrice(price);
        String answer = String.join("\n", "<할인 후 예상 결제 금액>", result);

        assertThat(console.toString()).isEqualToIgnoringNewLines(answer);
    }

    @ParameterizedTest
    @ValueSource(strings = {"산타", "트리", "별", "없음"})
    void checkPrintBadge(String badge) {
        OutputView.printBadge(badge);
        String answer = String.join("\n", "<12월 이벤트 배지>", badge);

        assertThat(console.toString()).isEqualToIgnoringNewLines(answer);
    }
}
