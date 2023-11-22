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
    private final InputView inputView;
    private final OutputView outputView;

    public EventController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printHelloMessage();
        RetryExecutor.execute(this::setBill, outputView::printErrorMessage);
        RetryExecutor.execute(this::setOrder, error -> {
            outputView.printErrorMessage(error);
            bill.clearOrder();
        });
        printResult();
    }

    private void setBill() {
        String input = inputView.inputDate().trim();
        bill = Bill.from(IntParser.parseIntOrThrow(input));
        eventHandler = EventHandler.from(bill);
    }

    private void setOrder() {
        String input = inputView.inputOrder();
        List<OrderDTO> orders = OrderParser.parseOrderOrThrow(input);
        orders.forEach(it -> bill.add(Order.create(it.menuName(), it.count())));
        bill.validateOnlyDrink();
    }

    private void printResult() {
        outputView.printEventPreviewTitle(bill.getDateValue());
        outputView.printOrder(bill.getAllOrders());
        outputView.printTotalPrice(bill.getTotalPrice());
        outputView.printGift(eventHandler.hasChampagneGift());
        outputView.printAllBenefit(eventHandler.getAllBenefit());
        outputView.printBenefitPrice(eventHandler.getTotalBenefitPrice());
        outputView.printAfterDiscountPrice(bill.getTotalPrice() - eventHandler.getTotalDiscountPrice());
        outputView.printBadge(Badge.getBadgeNameWithBenefitPrice(eventHandler.getTotalBenefitPrice()));
    }
}
