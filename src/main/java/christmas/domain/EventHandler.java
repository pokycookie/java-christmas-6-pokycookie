package christmas.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventHandler {
    private final Bill bill;

    private EventHandler(Bill bill) {
        this.bill = bill;
    }

    public static EventHandler from(Bill bill) {
        return new EventHandler(bill);
    }

    public boolean hasChampagneGift() {
        return Event.CHAMPAGNE.checkCondition(bill);
    }

    public int getTotalDiscountPrice() {
        return Arrays.stream(Event.values())
                .filter(Event::isDiscount)
                .filter(it -> it.checkCondition(bill))
                .map(it -> it.getBenefit(bill))
                .reduce(0, Integer::sum);
    }

    public int getTotalBenefitPrice() {
        int discount = getTotalDiscountPrice();
        if (hasChampagneGift()) {
            return discount + Event.CHAMPAGNE.getBenefit(bill);
        }
        return discount;
    }

    public List<Benefit> getAllBenefit() {
        List<Benefit> result = new ArrayList<>();
        Arrays.stream(Event.values())
                .filter(it -> it.checkCondition(bill))
                .forEach(it -> {
                    String name = it.getEventName();
                    int price = it.getBenefit(bill);
                    result.add(new Benefit(name, price));
                });
        return result;
    }
}
