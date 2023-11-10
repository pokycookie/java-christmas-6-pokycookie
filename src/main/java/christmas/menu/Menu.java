package christmas.menu;

import java.util.Arrays;

public enum Menu {
    SOUP("양송이수프", 6000, MenuType.APPETIZER),
    TAPAS("타파스", 5500, MenuType.APPETIZER),
    SALAD("시저샐러드", 8000, MenuType.APPETIZER),
    STEAK("티본스테이크", 55000, MenuType.MAIN),
    BARBECUE("바비큐립", 54000, MenuType.MAIN),
    SEAFOOD_PASTA("해산물파스타", 35000, MenuType.MAIN),
    CHRISTMAS_PASTA("크리스마스파스타", 25000, MenuType.MAIN),
    CAKE("초코케이크", 15000, MenuType.DESSERT),
    ICE_CREAM("아이스크림", 5000, MenuType.DESSERT),
    COLA("제로콜라", 3000, MenuType.DRINK),
    WINE("레드와인", 60000, MenuType.DRINK),
    CHAMPAGNE("샴페인", 25000, MenuType.DRINK);

    private final String menuName;
    private final int price;
    private final MenuType type;

    Menu(String menuName, int price, MenuType type) {
        this.menuName = menuName;
        this.price = price;
        this.type = type;
    }

    public static Menu from(String menuName) {
        return Arrays.stream(Menu.values())
                .filter(it -> menuName.equals(it.menuName))
                .findAny()
                .orElse(null);
    }

    public String getMenuName() {
        return menuName;
    }

    public int getPrice() {
        return price;
    }

    public MenuType getType() {
        return type;
    }
}
