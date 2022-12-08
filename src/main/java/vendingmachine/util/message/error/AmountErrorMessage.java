package vendingmachine.util.message.error;

import vendingmachine.model.coin.Coin;

public enum AmountErrorMessage implements ErrorMessage {


    INVALID_RANGE("금액은 0원 이상의 숫자여야 합니다 (최대 %d원).", Integer.MAX_VALUE),
    INVALID_UNIT("금액은 %d원 단위여야 합니다.", Coin.minimumUnit());

    private final String baseMessage;
    private final int number;

    AmountErrorMessage(String baseMessage, int number) {
        this.baseMessage = baseMessage;
        this.number = number;
    }

    @Override
    public String fullMessage() {
        return String.format(ErrorMessage.PREDICATE + baseMessage, number);
    }
}
