package christmas.domain;

import christmas.config.Menu;
import christmas.config.MenuType;

import java.util.function.Function;
import java.util.function.Predicate;

public enum Event {
    CHRISTMAS(
            "크리스마스 디데이 할인",
            true,
            Bill::isNotPassedChristmas,
            bill -> bill.timePassedSinceFirstDay() * 100 + 1000
    ),
    WEEKDAY(
            "평일 할인",
            true,
            bill -> !bill.isWeekend(),
            bill -> bill.getTypeCount(MenuType.DESSERT) * 2023
    ),
    WEEKEND(
            "주말 할인",
            true,
            Bill::isWeekend,
            bill -> bill.getTypeCount(MenuType.MAIN) * 2023
    ),
    SPECIAL(
            "특별 할인",
            true,
            Bill::isSpecialDay,
            bill -> 1000
    ),
    CHAMPAGNE(
            "증정 이벤트",
            false,
            bill -> bill.getTotalPrice() >= 120000,
            bill -> Menu.CHAMPAGNE.getPrice()
    );

    private static final int ALL_EVENT_THRESHOLD = 10000;

    private final String eventName;
    private final boolean isDiscount;
    private final Predicate<Bill> condition;
    private final Function<Bill, Integer> benefit;

    Event(
            String eventName,
            boolean isDiscount,
            Predicate<Bill> condition,
            Function<Bill, Integer> benefit
    ) {
        this.eventName = eventName;
        this.isDiscount = isDiscount;
        this.condition = condition;
        this.benefit = benefit;
    }

    public String getEventName() {
        return eventName;
    }

    public boolean isDiscount() {
        return isDiscount;
    }

    public boolean checkCondition(Bill bill) {
        return condition.test(bill) && bill.getTotalPrice() >= ALL_EVENT_THRESHOLD;
    }

    public int getBenefit(Bill bill) {
        return benefit.apply(bill);
    }
}
