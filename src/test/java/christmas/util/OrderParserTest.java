package christmas.util;

import christmas.dto.OrderDTO;
import christmas.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderParserTest {
    @DisplayName("주문이 제대로 파싱되는지 확인")
    @ParameterizedTest
    @MethodSource("parseOrderResult")
    void checkParseOrderOrThrow(String orderSequence, List<OrderDTO> answer) {
        assertThat(OrderParser.parseOrderOrThrow(orderSequence)).isEqualTo(answer);
    }
    static Stream<Arguments> parseOrderResult() {
        return Stream.of(
                Arguments.of("타파스-1,제로콜라-2,티본스테이크-5", List.of(
                        new OrderDTO("타파스", 1),
                        new OrderDTO("제로콜라", 2),
                        new OrderDTO("티본스테이크", 5)
                )),
                Arguments.of("타파스 - 1, 제로콜라- 2, 티본 스테이크 -6", List.of(
                        new OrderDTO("타파스", 1),
                        new OrderDTO("제로콜라", 2),
                        new OrderDTO("티본스테이크", 6)
                )),
                Arguments.of("타파스-1", List.of(
                        new OrderDTO("타파스", 1)
                )),
                Arguments.of("해산물파스타-3 , ", List.of(
                        new OrderDTO("해산물파스타", 3)
                ))
        );
    }

    @DisplayName("잘못된 형식의 주문을 입력하면 예외가 발생")
    @ParameterizedTest
    @ValueSource(strings = {"타파스: 1, 제로콜라: 2", "타파스-1 제로콜라-2 파스타-3", "타파스 -", " - 3"})
    void checkParseOrderOrThrowWithWrongPattern(String orderSequence) {
        assertThatThrownBy(() -> OrderParser.parseOrderOrThrow(orderSequence))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.WRONG_ORDER.getMessage());
    }
}
