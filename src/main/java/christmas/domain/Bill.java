package christmas.domain;

import christmas.dto.OrderDTO;
import christmas.exception.ErrorMessage;
import christmas.menu.MenuType;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private static final int INIT_VALUE = 0;
    private static final int VALID_CONDITION = 0;

    private final List<Order> orders = new ArrayList<>();
    private final Date date;

    private Bill(Date date) {
        this.date = date;
    }

    public static Bill from(Date date) {
        return new Bill(date);
    }

    public void add(Order order) {
        validateDuplicates(order);
        orders.add(order);
    }

    public void clearOrder() {
        orders.clear();
    }

    public int getTotalPrice() {
        return orders.stream()
                .map(Order::getPrice)
                .reduce(0, Integer::sum);
    }

    public int getTypeCount(MenuType type) {
        return orders.stream()
                .map(it -> it.getTypeCount(type))
                .reduce(INIT_VALUE, Integer::sum);
    }

    public Date getDate() {
        return date;
    }

    public List<OrderDTO> getAllOrders() {
        return orders.stream().map(Order::getOrder).toList();
    }

    private void validateDuplicates(Order newOrder) {
        long duplicated = orders.stream()
                .filter(it -> it.compareWithMenu(newOrder))
                .count();
        if (duplicated != VALID_CONDITION) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_ORDER.getMessage());
        }
    }
}
