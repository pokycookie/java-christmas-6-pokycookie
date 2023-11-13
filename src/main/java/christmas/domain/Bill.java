package christmas.domain;

import christmas.dto.OrderDTO;
import christmas.menu.MenuType;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private final List<Order> orders = new ArrayList<>();
    private final Date date;

    private Bill(Date date) {
        this.date = date;
    }

    public static Bill from(Date date) {
        return new Bill(date);
    }

    public void add(Order order) {
        orders.add(order);
    }

    public int getTotalPrice() {
        return orders.stream()
                .map(Order::getPrice)
                .reduce(0, Integer::sum);
    }

    public int getTypeCount(MenuType type) {
        return orders.stream()
                .filter(it -> it.getType() == type)
                .map(Order::getCount)
                .reduce(0, Integer::sum);
    }

    public Date getDate() {
        return date;
    }

    public List<OrderDTO> getAllOrders() {
        return orders.stream().map(Order::getOrder).toList();
    }
}
