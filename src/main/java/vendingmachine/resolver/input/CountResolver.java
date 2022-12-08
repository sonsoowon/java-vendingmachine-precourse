package vendingmachine.resolver.input;

import vendingmachine.util.message.error.CountErrorMessage;
import vendingmachine.valid.CountValidator;

public class CountResolver implements InputResolver<Integer> {
    public CountResolver() {}

    @Override
    public Integer resolve(String input) throws IllegalArgumentException {
        int count = getIntFrom(input);
        CountValidator.validate(count);
        return count;
    }

    private int getIntFrom(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            String message = CountErrorMessage.INVALID_RANGE.fullMessage();
            throw new IllegalArgumentException(message, e);
        }
    }
}
