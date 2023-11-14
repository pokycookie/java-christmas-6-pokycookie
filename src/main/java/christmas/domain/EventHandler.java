package christmas.domain;

import christmas.config.Constant;
import christmas.dto.BenefitDTO;

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
                .reduce(Constant.INIT_VALUE, Integer::sum);
    }

    public int getTotalBenefitPrice() {
        int discount = getTotalDiscountPrice();
        if (hasChampagneGift()) {
            return discount + Event.CHAMPAGNE.getBenefit(bill);
        }
        return discount;
    }

    public List<BenefitDTO> getAllBenefit() {
        List<BenefitDTO> result = new ArrayList<>();
        Arrays.stream(Event.values())
                .filter(it -> it.checkCondition(bill))
                .filter(it -> it.getBenefit(bill) > Constant.FILTER_CONDITION)
                .forEach(it -> {
                    String name = it.getEventName();
                    int price = it.getBenefit(bill);
                    result.add(new BenefitDTO(name, price));
                });
        return result;
    }
}
