package christmas.view;

import christmas.dto.BenefitDTO;
import christmas.dto.OrderDTO;

import java.util.List;

public class OutputView {
    private OutputView() {
        // 인스턴스 생성 방지
    }

    private static void printMessage(ViewMessage message) {
        System.out.println(message.getMessage());
    }

    private static void printFormat(ViewMessage message, Object... args) {
        System.out.printf(message.getMessage(), args);
        newLine();
    }

    private static void printTitle(ViewTitle title) {
        System.out.println(title.getTitle());
    }

    public static void printHelloMessage() {
        printMessage(ViewMessage.HELLO);
    }

    public static void printEventPreviewTitle(int date) {
        printFormat(ViewMessage.EVENT_PREVIEW_TITLE, date);
        newLine();
    }

    public static void printOrder(List<OrderDTO> orders) {
        printTitle(ViewTitle.ORDER_MENU);
        orders.forEach(it -> printFormat(ViewMessage.ORDER_FORMAT, it.menuName(), it.count()));
        newLine();
    }

    public static void printTotalPrice(int price) {
        printTitle(ViewTitle.TOTAL_PRICE);
        printFormat(ViewMessage.PRICE_FORMAT, price);
        newLine();
    }

    public static void printGift(boolean hasGift) {
        printTitle(ViewTitle.GIFT_MENU);
        if (hasGift) {
            printMessage(ViewMessage.CHAMPAGNE);
        } else {
            printMessage(ViewMessage.NOTHING);
        }
        newLine();
    }

    public static void printAllBenefit(List<BenefitDTO> benefits) {
        printTitle(ViewTitle.BENEFIT_LIST);
        if (benefits.isEmpty()) {
            printMessage(ViewMessage.NOTHING);
        } else {
            benefits.forEach(it -> printFormat(ViewMessage.BENEFIT_FORMAT, it.EventName(), it.price()));
        }
        newLine();
    }

    public static void printBenefitPrice(int price) {
        printTitle(ViewTitle.BENEFIT_PRICE);
        printFormat(ViewMessage.PRICE_FORMAT, -price);
        newLine();
    }

    public static void printAfterDiscountPrice(int price) {
        printTitle(ViewTitle.AFTER_DISCOUNT_PRICE);
        printFormat(ViewMessage.PRICE_FORMAT, price);
        newLine();
    }

    public static void printBadge(String badge) {
        printTitle(ViewTitle.BADGE);
        System.out.println(badge);
    }

    public static void newLine() {
        System.out.println();
    }
}
