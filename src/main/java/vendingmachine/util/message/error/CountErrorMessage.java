package vendingmachine.util.message.error;

import vendingmachine.valid.CountValidator;

public enum CountErrorMessage implements ErrorMessage {

    INVALID_RANGE(String.format("수량은 %d개 이상이어야 합니다. (최대 %d개)", CountValidator.MIN_COUNT, Integer.MAX_VALUE));
    private final String message;

    CountErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String fullMessage() {
        return ErrorMessage.PREDICATE + message;
    }
}
