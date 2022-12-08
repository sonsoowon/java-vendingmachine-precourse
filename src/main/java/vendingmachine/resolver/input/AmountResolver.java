package vendingmachine.resolver.input;

import vendingmachine.model.amount.Amount;
import vendingmachine.util.message.error.AmountErrorMessage;

public class AmountResolver implements InputResolver<Amount> {

    public AmountResolver() {}

    @Override
    public Amount resolve(String input) throws IllegalArgumentException {
        int amount = getIntFrom(input);
        return Amount.of(amount);
    }

    private int getIntFrom(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            String message = AmountErrorMessage.INVALID_RANGE.fullMessage();
            throw new IllegalArgumentException(message, e);
        }
    }
}
