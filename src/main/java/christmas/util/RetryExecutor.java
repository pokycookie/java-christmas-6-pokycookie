package christmas.util;

import christmas.view.OutputView;

public class RetryExecutor {
    private RetryExecutor() {
        // 인스턴스 생성 방지
    }

    public static void execute(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException error) {
            OutputView.printErrorMessage(error);
            execute(action);
        }
    }

    public static void execute(Runnable action, Runnable onError) {
        try {
            action.run();
        } catch (IllegalArgumentException error) {
            OutputView.printErrorMessage(error);
            onError.run();
            execute(action, onError);
        }
    }
}
