package christmas.domain;

import christmas.dto.OrderDTO;
import christmas.exception.ErrorMessage;
import christmas.menu.MenuType;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private static final int INIT_VALUE = 0;
    private static final int VALID_CONDITION = 0;
    private static final int MAX_ORDER = 20;

    private final List<Order> orders = new ArrayList<>();
    private final Date date;

    private Bill(Date date) {
        this.date = date;
    }

    public static Bill from(int date) {
        return new Bill(Date.from(date));
    }

    public void add(Order order) {
        validateDuplicates(order);
        validateMaxOrder(order);
        orders.add(order);
    }
    private void validateDuplicates(Order newOrder) {
        long duplicated = orders.stream()
                .filter(it -> it.compareWithMenu(newOrder))
                .count();
        if (duplicated != VALID_CONDITION) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_ORDER.getMessage());
        }
    }
    private void validateMaxOrder(Order newOrder) {
        int currCount = orders.stream()
                .map(it -> it.getOrder().count())
                .reduce(INIT_VALUE, Integer::sum);
        if (currCount + newOrder.getOrder().count() > MAX_ORDER) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_ORDER.getMessage());
        }
    }

    public void clearOrder() {
        orders.clear();
    }

    public int getTotalPrice() {
        return orders.stream()
                .map(Order::getPrice)
                .reduce(INIT_VALUE, Integer::sum);
    }

    public int getTypeCount(MenuType type) {
        return orders.stream()
                .map(it -> it.getTypeCount(type))
                .reduce(INIT_VALUE, Integer::sum);
    }

    public List<OrderDTO> getAllOrders() {
        return orders.stream().map(Order::getOrder).toList();
    }

    // 아래는 Date 관련 method

    public boolean isWeekend() {
        return date.isWeekend();
    }
    public boolean isSpecialDay() {
        return date.isSpecialDay();
    }
    public boolean isNotPassedChristmas() {
        return date.isNotPassedChristmas();
    }
    public int timePassedSinceFirstDay() {
        return date.timePassedSinceFirstDay();
    }
}
