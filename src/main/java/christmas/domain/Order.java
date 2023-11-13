package christmas.domain;

import christmas.dto.OrderDTO;
import christmas.menu.Menu;
import christmas.menu.MenuType;

import java.util.List;

public class Order {
    private static final int INIT_VALUE = 0;

    private final Menu menu;
    private final int count;

    private Order(Menu menu, int count) {
        this.menu = menu;
        this.count = count;
    }

    public static Order create(String menuName, int count) {
        Menu menu = Menu.from(menuName);
        return new Order(menu, count);
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
                .reduce(INIT_VALUE, Integer::sum);
    }
    public static int accumulateCount(List<Order> orders, MenuType type) {
        return orders.stream()
                .filter(it -> it.menu.getType() == type)
                .map(it -> it.count)
                .reduce(INIT_VALUE, Integer::sum);
    }
}
