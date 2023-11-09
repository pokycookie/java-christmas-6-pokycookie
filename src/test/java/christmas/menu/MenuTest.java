package christmas.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuTest {
    @DisplayName("메뉴 이름에 맞는 Menu를 반환하는지 확인")
    @Test
    void checkMenuFromMenuName() {
        assertThat(Menu.from("해산물파스타")).isEqualTo(Menu.SEAFOOD_PASTA);
    }
}
