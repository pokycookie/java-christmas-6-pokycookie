package christmas.controller;

import christmas.domain.Bill;
import christmas.domain.Date;
import christmas.domain.EventHandler;
import christmas.domain.Order;
import christmas.dto.OrderDTO;
import christmas.exception.ErrorMessage;
import christmas.menu.Menu;
import christmas.util.IntParser;
import christmas.util.OrderParser;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.ViewMessage;

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
        printBadge();
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
        orders.forEach(it -> {
            Menu menu = getMenu(it.menuName());
            bill.add(Order.create(menu, it.count()));
        });
    }

    private Menu getMenu(String menuName) {
        Menu menu = Menu.from(menuName);
        if (menu == null) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_ORDER.getMessage());
        }
        return menu;
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
    }

    private void printBadge() {
        String badge = ViewMessage.NOTHING.getMessage();
        int benefitPrice = eventHandler.getTotalBenefitPrice();
        if (benefitPrice >= 20000) {
            badge = "산타";
        } else if (benefitPrice >= 10000) {
            badge = "트리";
        } else if (benefitPrice >= 5000) {
            badge = "별";
        }
        OutputView.printBadge(badge);
    }
}
