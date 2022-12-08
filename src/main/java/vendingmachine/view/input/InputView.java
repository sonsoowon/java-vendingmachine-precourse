package vendingmachine.view.input;

import camp.nextstep.edu.missionutils.Console;

public interface InputView<T> {

    T resolve(String input);

    void printMessage();

    default T readInput() {
        printMessage();
        return getValidInput();
    }

    default T getValidInput() {
        try {
            String input = Console.readLine();
            return resolve(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getValidInput();
        }
    }

    enum InputMessage {

        OWN_AMOUNT("자판기가 보유하고 있는 금액을 입력해 주세요."),
        PRODUCT_INFO("상품명과 가격, 수량을 입력해 주세요."),
        INSERT_AMOUNT("투입 금액을 입력해 주세요."),
        PURCHASE_PRODUCT("구매할 상품명을 입력해 주세요.");

        public final String message;

        InputMessage(String message) {
            this.message = message;
        }

        public String fullMessage() {
            return message;
        }

    }

}