package vendingmachine.util.message.error;

import vendingmachine.util.message.error.ErrorMessage;

public enum OperationErrorMessage implements ErrorMessage {
    INVALID_MINUS("현재 금액보다 큰 값을 뺄 수 없습니다."),
    INVALID_MULTIPLICATION("음수를 곱할 수 없습니다.");

    private final String message;

    OperationErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String fullMessage() {
        return ErrorMessage.PREDICATE + message;
    }
}
