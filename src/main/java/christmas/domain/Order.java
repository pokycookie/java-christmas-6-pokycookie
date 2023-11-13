package christmas.domain;

import christmas.dto.OrderDTO;
import christmas.menu.Menu;
import christmas.menu.MenuType;

public class Order {
    private static final int NOTHING = 0;

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

    public int getTypeCount(MenuType type) {
        if (menu.getType() == type) {
            return count;
        }
        return NOTHING;
    }

    public OrderDTO getOrder() {
        return new OrderDTO(menu.getMenuName(), count);
    }
}
