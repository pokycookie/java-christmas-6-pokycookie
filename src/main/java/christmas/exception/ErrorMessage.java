package christmas.exception;

public enum ErrorMessage {
    WRONG_DATE("유효하지 않은 날짜입니다."),
    WRONG_ORDER("유효하지 않은 주문입니다.");

    private static final String PREFIX = "[ERROR]";
    private static final String SUFFIX = "다시 입력해 주세요.";

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return String.join(" ", PREFIX, message, SUFFIX);
    }
}
