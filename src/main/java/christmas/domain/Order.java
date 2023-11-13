package christmas.domain;

import christmas.dto.OrderDTO;
import christmas.menu.Menu;
import christmas.menu.MenuType;

public class Order {
    private final Menu menu;
    private final int count;

    private Order(Menu menu, int count) {
        this.menu = menu;
        this.count = count;
    }

    public static Order create(Menu menu, int count) {
        return new Order(menu, count);
    }

    public int getPrice() {
        return menu.getPrice() * count;
    }

    public MenuType getType() {
        return menu.getType();
    }

    public int getCount() {
        return count;
    }

    public OrderDTO getOrder() {
        return new OrderDTO(menu.getMenuName(), count);
    }
}
