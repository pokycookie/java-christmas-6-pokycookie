package christmas.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private String printAndConsoleRead(ViewMessage message) {
        System.out.println(message.getMessage());
        return Console.readLine();
    }

    public String inputDate() {
        return printAndConsoleRead(ViewMessage.INPUT_DATE);
    }

    public String inputOrder() {
        return printAndConsoleRead(ViewMessage.INPUT_ORDER);
    }
}
