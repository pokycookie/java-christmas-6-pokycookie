package christmas.config;

public enum ErrorMessage {
    WRONG_DATE("유효하지 않은 날짜입니다."),
    WRONG_ORDER("유효하지 않은 주문입니다."),
    OVER_MAX_ORDER("메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다."),
    ONLY_DRINK_ORDER("음료만 주문할 수 없습니다.");

    private static final String PREFIX = "[ERROR]";
    private static final String SUFFIX = "다시 입력해 주세요.";
    private static final String DELIMITER = " ";

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return String.join(DELIMITER, PREFIX, message, SUFFIX);
    }
}
