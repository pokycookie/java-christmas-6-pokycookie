package christmas.menu;

import christmas.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuTest {
    @DisplayName("메뉴 이름에 맞는 Menu를 반환하는지 확인")
    @Test
    void checkMenuFromMenuName() {
        assertThat(Menu.from("해산물파스타")).isEqualTo(Menu.SEAFOOD_PASTA);
    }

    @DisplayName("메뉴판에 없는 메뉴를 입력하는 경우 에러를 반환")
    @Test
    void checkWrongMenuFromMenuName() {
        assertThatThrownBy(() -> Menu.from("아이스얼그레이"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.WRONG_ORDER.getMessage());
    }
}
