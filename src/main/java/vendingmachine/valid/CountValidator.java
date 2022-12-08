package vendingmachine.valid;

import vendingmachine.util.message.error.CountErrorMessage;

import java.util.Map;

public class CountValidator {
    public static final int MIN_COUNT = 1;

    private CountValidator() {}

    public static void validate(Map<?, Integer> objectCounts) {
        if(!allValidCounts(objectCounts)) {
            String message = CountErrorMessage.INVALID_RANGE.fullMessage();
            throw new IllegalArgumentException(message);
        }
    }

    public static void validate(int count) {
        if(count < MIN_COUNT) {
            String message = CountErrorMessage.INVALID_RANGE.fullMessage();
            throw new IllegalArgumentException(message);
        }
    }

    private static boolean allValidCounts(Map<?, Integer> objectCounts) {
        return objectCounts.values().stream()
                .allMatch(count -> count >= MIN_COUNT);
    }
}
