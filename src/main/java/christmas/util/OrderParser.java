package christmas.util;

import christmas.dto.OrderDTO;
import christmas.config.ErrorMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class OrderParser {
    private static final String SEQUENCE_DELIMITER = ",";
    private static final String ORDER_DELIMITER = "-";
    private static final String ORDER_PATTERN = "[^-]+-[0-9]+";
    private static final String REPLACE_TARGET = " ";
    private static final String REPLACEMENT = "";

    private OrderParser() {
        // 인스턴스 생성 방지
    }

    public static List<OrderDTO> parseOrderOrThrow(String orderSequence) {
        List<OrderDTO> result = new ArrayList<>();
        for (String order : splitOrder(trimAll(orderSequence))) {
            result.add(parseOrderDTO(order));
        }
        return result;
    }

    private static List<String> splitOrder(String orderSequence) {
        return List.of(orderSequence.split(SEQUENCE_DELIMITER));
    }

    private static OrderDTO parseOrderDTO(String order) {
        validateOrderPattern(order);
        String[] split = order.split(ORDER_DELIMITER);
        try {
            return new OrderDTO(split[0], IntParser.parseIntOrThrow(split[1]));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_ORDER.getMessage());
        }
    }

    private static String trimAll(String target) {
        return target.replaceAll(REPLACE_TARGET, REPLACEMENT);
    }

    private static void validateOrderPattern(String order) {
        if (!Pattern.matches(ORDER_PATTERN, order)) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_ORDER.getMessage());
        }
    }
}
