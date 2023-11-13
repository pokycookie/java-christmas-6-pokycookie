package christmas.controller;

import christmas.domain.*;
import christmas.dto.OrderDTO;
import christmas.util.IntParser;
import christmas.util.OrderParser;
import christmas.util.RetryExecutor;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.List;

public class EventController {
    private Bill bill;
    private EventHandler eventHandler;

    public void run() {
        OutputView.printHelloMessage();
        RetryExecutor.execute(this::setBill);
        RetryExecutor.execute(this::setOrder, bill::clearOrder);
        printResult();
    }

    private void setBill() {
        String input = InputView.inputDate();
        bill = Bill.from(IntParser.parseIntOrThrow(input));
        eventHandler = EventHandler.from(bill);
    }

    private void setOrder() {
        String input = InputView.inputOrder();
        List<OrderDTO> orders = OrderParser.parseOrderOrThrow(input);
        orders.forEach(it -> bill.add(Order.create(it.menuName(), it.count())));
    }

    private void printResult() {
        OutputView.printEventPreviewTitle(bill.getDateValue());
        OutputView.printOrder(bill.getAllOrders());
        OutputView.printTotalPrice(bill.getTotalPrice());
        OutputView.printGift(eventHandler.hasChampagneGift());
        OutputView.printAllBenefit(eventHandler.getAllBenefit());
        OutputView.printBenefitPrice(eventHandler.getTotalBenefitPrice());
        OutputView.printAfterDiscountPrice(bill.getTotalPrice() - eventHandler.getTotalDiscountPrice());
        OutputView.printBadge(Badge.getBadgeNameWithBenefitPrice(eventHandler.getTotalBenefitPrice()));
    }
}
