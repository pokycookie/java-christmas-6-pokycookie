package christmas.util;

import java.util.function.Consumer;

public class RetryExecutor {
    private RetryExecutor() {
        // 인스턴스 생성 방지
    }

    public static void execute(Runnable action, Consumer<IllegalArgumentException> onError) {
        try {
            action.run();
        } catch (IllegalArgumentException error) {
            onError.accept(error);
            execute(action, onError);
        }
    }
}
