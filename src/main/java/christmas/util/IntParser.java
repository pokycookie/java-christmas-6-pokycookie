package christmas.util;

import christmas.config.ErrorMessage;

public class IntParser {
    private static final int MAX_STRING_LENGTH = 11;

    private IntParser() {
        // 인스턴스 생성 방지
    }

    public static int parseIntOrThrow(String numeric) {
        validateNumericStringLength(numeric);
        long parsed = parseLongOrThrow(numeric);
        validateIntRange(parsed);
        return Integer.parseInt(numeric);
    }

    private static long parseLongOrThrow(String numeric) {
        try {
            return Long.parseLong(numeric);
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_DATE.getMessage());
        }
    }

    private static void validateNumericStringLength(String numeric) {
        if (numeric.length() > MAX_STRING_LENGTH) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_DATE.getMessage());
        }
    }

    private static void validateIntRange(long number) {
        if (number > Integer.MAX_VALUE || number < Integer.MIN_VALUE) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_DATE.getMessage());
        }
    }
}
