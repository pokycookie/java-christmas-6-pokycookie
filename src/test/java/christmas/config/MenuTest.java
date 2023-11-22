package christmas.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuTest {
    @DisplayName("from 동작 확인")
    @ParameterizedTest
    @MethodSource("menuTarget")
    void checkMenuFromMenuName(String menuName, Menu answer) {
        assertThat(Menu.from(menuName)).isEqualTo(answer);
    }

    static Stream<Arguments> menuTarget() {
        return Stream.of(
                Arguments.of("양송이수프", Menu.SOUP),
                Arguments.of("해산물파스타", Menu.SEAFOOD_PASTA)
        );
    }

    @DisplayName("메뉴판에 없는 메뉴를 입력시 예외 확인")
    @Test
    void checkWrongMenuFromMenuName() {
        assertThatThrownBy(() -> Menu.from("아이스얼그레이"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.WRONG_ORDER.getMessage());
    }
}
