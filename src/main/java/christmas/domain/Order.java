package christmas.domain;

import christmas.config.Constant;
import christmas.dto.OrderDTO;
import christmas.config.ErrorMessage;
import christmas.config.Menu;
import christmas.config.MenuType;

import java.util.List;

public class Order {
    private final Menu menu;
    private final int count;

    private Order(Menu menu, int count) {
        validateCount(count);
        this.menu = menu;
        this.count = count;
    }

    public static Order create(String menuName, int count) {
        Menu menu = Menu.from(menuName);
        return new Order(menu, count);
    }

    private void validateCount(int count) {
        if (count < Constant.MIN_ORDER) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_ORDER.getMessage());
        }
    }

    public int getPrice() {
        return menu.getPrice() * count;
    }

    public boolean isSameMenu(Order target) {
        return menu == target.menu;
    }

    public OrderDTO getOrder() {
        return new OrderDTO(menu.getMenuName(), count);
    }

    public static int accumulateCount(List<Order> orders) {
        return orders.stream()
                .map(it -> it.count)
                .reduce(Constant.INIT_VALUE, Integer::sum);
    }
    public static int accumulateCount(List<Order> orders, MenuType type) {
        return orders.stream()
                .filter(it -> it.menu.getType() == type)
                .map(it -> it.count)
                .reduce(Constant.INIT_VALUE, Integer::sum);
    }
}
