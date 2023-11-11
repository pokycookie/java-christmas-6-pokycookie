package christmas.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private InputView() {
        // 인스턴스 생성 방지
    }

    private static String printAndConsoleRead(ViewMessage message) {
        System.out.println(message.getMessage());
        return Console.readLine();
    }

    public static String inputDate() {
        return printAndConsoleRead(ViewMessage.INPUT_DATE);
    }

    public static String inputOrder() {
        return printAndConsoleRead(ViewMessage.INPUT_ORDER);
    }
}
