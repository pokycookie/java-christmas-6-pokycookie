package christmas.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {
    @Test
    void checkOrderPrice() {
        assertThat(Order.create("해산물파스타", 2).getPrice())
                .isEqualTo(70000);
    }
}
