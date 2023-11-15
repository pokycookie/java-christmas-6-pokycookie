package christmas.view;

import christmas.dto.BenefitDTO;
import christmas.dto.OrderDTO;

import java.util.List;

public class OutputView {
    private void printMessage(ViewMessage message) {
        System.out.println(message.getMessage());
    }

    private void printFormat(ViewMessage message, Object... args) {
        System.out.printf(message.getMessage(), args);
        newLine();
    }

    private void printTitle(ViewTitle title) {
        System.out.println(title.getTitle());
    }

    public void printHelloMessage() {
        printMessage(ViewMessage.HELLO);
    }

    public void printEventPreviewTitle(int date) {
        printFormat(ViewMessage.EVENT_PREVIEW_TITLE, date);
        newLine();
    }

    public void printOrder(List<OrderDTO> orders) {
        printTitle(ViewTitle.ORDER_MENU);
        orders.forEach(it -> printFormat(ViewMessage.ORDER_FORMAT, it.menuName(), it.count()));
        newLine();
    }

    public void printTotalPrice(int price) {
        printTitle(ViewTitle.TOTAL_PRICE);
        printFormat(ViewMessage.PRICE_FORMAT, price);
        newLine();
    }

    public void printGift(boolean hasGift) {
        printTitle(ViewTitle.GIFT_MENU);
        if (hasGift) {
            printMessage(ViewMessage.CHAMPAGNE);
            newLine();
            return;
        }
        printMessage(ViewMessage.NOTHING);
        newLine();
    }

    public void printAllBenefit(List<BenefitDTO> benefits) {
        printTitle(ViewTitle.BENEFIT_LIST);
        if (benefits.isEmpty()) {
            printMessage(ViewMessage.NOTHING);
            newLine();
            return;
        }
        benefits.forEach(it -> printFormat(ViewMessage.BENEFIT_FORMAT, it.EventName(), it.price()));
        newLine();
    }

    public void printBenefitPrice(int price) {
        printTitle(ViewTitle.BENEFIT_PRICE);
        printFormat(ViewMessage.PRICE_FORMAT, -price);
        newLine();
    }

    public void printAfterDiscountPrice(int price) {
        printTitle(ViewTitle.AFTER_DISCOUNT_PRICE);
        printFormat(ViewMessage.PRICE_FORMAT, price);
        newLine();
    }

    public void printBadge(String badge) {
        printTitle(ViewTitle.BADGE);
        System.out.println(badge);
    }

    public void printErrorMessage(IllegalArgumentException error) {
        newLine();
        System.out.println(error.getMessage());
        newLine();
    }

    public void newLine() {
        System.out.println();
    }
}
