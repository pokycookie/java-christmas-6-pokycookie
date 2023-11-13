package christmas.controller;

import christmas.domain.*;
import christmas.dto.OrderDTO;
import christmas.exception.ErrorMessage;
import christmas.util.IntParser;
import christmas.util.OrderParser;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.List;

public class EventController {
    Bill bill;
    EventHandler eventHandler;
    int date;

    public void run() {
        printHello();
        inputDate();
        inputOrder();
        printResult();
    }

    private void printHello() {
        OutputView.printHelloMessage();
    }

    private void inputDate() {
        try {
            String input = InputView.inputDate();
            date = IntParser.parseIntOrThrow(input);
            bill = Bill.from(Date.from(date));
            eventHandler = EventHandler.from(bill);
        } catch (IllegalArgumentException error) {
            OutputView.printErrorMessage(error);
            inputDate();
        }

    }

    private void inputOrder() {
        try {
            String input = InputView.inputOrder();
            List<OrderDTO> orders = OrderParser.parseOrderOrThrow(input);
            validateDuplicates(orders);
            addAllOrder(orders);
        } catch (IllegalArgumentException error) {
            OutputView.printErrorMessage(error);
            inputOrder();
        }

    }

    private void addAllOrder(List<OrderDTO> orders) {
        orders.forEach(it -> bill.add(Order.create(it.menuName(), it.count())));
    }

    private void validateDuplicates(List<OrderDTO> orders) {
        if (orders.size() != orders.stream().map(OrderDTO::menuName).distinct().count()) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_ORDER.getMessage());
        }
    }

    private void printResult() {
        OutputView.printEventPreviewTitle(date);
        OutputView.printOrder(bill.getAllOrders());
        OutputView.printTotalPrice(bill.getTotalPrice());
        OutputView.printGift(eventHandler.hasChampagneGift());
        OutputView.printAllBenefit(eventHandler.getAllBenefit());
        OutputView.printBenefitPrice(eventHandler.getTotalBenefitPrice());
        OutputView.printAfterDiscountPrice(bill.getTotalPrice() - eventHandler.getTotalDiscountPrice());
        OutputView.printBadge(Badge.getBadgeNameWithBenefitPrice(eventHandler.getTotalBenefitPrice()));
    }
}