package christmas.view;

public enum ViewTitle {
    ORDER_MENU("주문 메뉴"),
    TOTAL_PRICE("할인 전 총주문 금액"),
    GIFT_MENU("증정 메뉴"),
    BENEFIT_LIST("혜택 내역"),
    BENEFIT_PRICE("총혜택 금액"),
    AFTER_DISCOUNT_PRICE("할인 후 예상 결제 금액"),
    BADGE("12월 이벤트 배지");

    private static final String LEFT_BRACKET = "<";
    private static final String RIGHT_BRACKET = ">";
    private final String title;

    ViewTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return String.join("", LEFT_BRACKET, title, RIGHT_BRACKET);
    }
}
