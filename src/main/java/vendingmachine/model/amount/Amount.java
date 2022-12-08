package vendingmachine.model.amount;

import vendingmachine.valid.AmountValidator;

public class Amount {
    private final int amount;

    private Amount(int amount) {
        this.amount = amount;
    }

    public static Amount of(int amount) throws IllegalArgumentException {
        AmountValidator.validate(amount);
        return new Amount(amount);
    }

    public int toInt() {
        return amount;
    }

    public boolean isBiggerThan(Amount other) {
        return this.amount > other.amount;
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}
