package christmas.domain;

import christmas.dto.OrderDTO;
import christmas.exception.ErrorMessage;
import christmas.menu.MenuType;

import java.util.ArrayList;
import java.util.List;

public class Bill implements CheckEventDate {
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
        orders.add(order);
        validateMaxOrder();
    }
    private void validateDuplicates(Order newOrder) {
        long duplicated = orders.stream()
                .filter(it -> it.isSameMenu(newOrder))
                .count();
        if (duplicated != VALID_CONDITION) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_ORDER.getMessage());
        }
    }
    private void validateMaxOrder() {
        if (Order.accumulateCount(orders) > MAX_ORDER) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_ORDER.getMessage());
        }
    }

    public void validateOnlyDrink() {
        if (Order.accumulateCount(orders) == getTypeCount(MenuType.DRINK)) {
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
        return Order.accumulateCount(orders, type);
    }

    public List<OrderDTO> getAllOrders() {
        return orders.stream().map(Order::getOrder).toList();
    }

    // 아래는 Date 관련 method

    @Override
    public boolean isWeekend() {
        return date.isWeekend();
    }
    @Override
    public boolean isSpecialDay() {
        return date.isSpecialDay();
    }
    @Override
    public boolean isNotPassedChristmas() {
        return date.isNotPassedChristmas();
    }
    @Override
    public int timePassedSinceFirstDay() {
        return date.timePassedSinceFirstDay();
    }

    public int getDateValue() {
        return date.getDateValue();
    }
}
