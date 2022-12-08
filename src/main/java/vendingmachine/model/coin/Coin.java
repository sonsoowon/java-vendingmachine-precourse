package vendingmachine.model.coin;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public enum Coin {
    COIN_500(500),
    COIN_100(100),
    COIN_50(50),
    COIN_10(10);

    private final int amount;

    Coin(final int amount) {
        this.amount = amount;
    }

    public static int minimumUnit() {
        return COIN_10.amount;
    }

    public static List<Coin> sortedValuesInDescendingOrder() {
        Comparator<Coin> compareByAmount = Comparator.comparingInt(a -> a.amount);

        return Arrays.stream(Coin.values())
                .sorted(compareByAmount.reversed())
                .collect(Collectors.toUnmodifiableList());
    }

    public static Coin getInstanceOf(int amount) {
        return Arrays.stream(Coin.values())
                .filter(coin -> coin.amount == amount)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public int getAmount() {
        return amount;
    }

}
