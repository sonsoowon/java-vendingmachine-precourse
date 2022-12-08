package vendingmachine.valid;

import vendingmachine.model.coin.Coin;
import vendingmachine.util.message.error.AmountErrorMessage;

public class AmountValidator {
    private AmountValidator() {
    }

    public static void validate(int amount) {
        checkNonNegative(amount);
        checkUnit(amount);
    }

    private static void checkNonNegative(int amount) {
        if (!nonNegative(amount)) {
            String message = AmountErrorMessage.INVALID_RANGE.fullMessage();
            throw new IllegalArgumentException(message);
        }
    }

    private static boolean nonNegative(int amount) {
        return amount >= 0;
    }

    private static void checkUnit(int amount) {
        if (!isCorrectUnit(amount)) {
            String message = AmountErrorMessage.INVALID_UNIT.fullMessage();
            throw new IllegalArgumentException(message);
        }
    }

    private static boolean isCorrectUnit(int amount) {
        return amount % Coin.minimumUnit() == 0;
    }
}
