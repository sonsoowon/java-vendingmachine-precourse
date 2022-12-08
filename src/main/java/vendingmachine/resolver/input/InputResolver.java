package vendingmachine.resolver.input;

import vendingmachine.util.message.error.AmountErrorMessage;

public interface InputResolver<T> {
    T resolve(String input);
}
