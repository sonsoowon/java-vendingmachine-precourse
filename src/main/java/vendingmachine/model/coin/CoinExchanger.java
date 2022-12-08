package vendingmachine.model.coin;

import vendingmachine.dto.CoinCountsDto;
import vendingmachine.model.amount.Amount;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public interface CoinExchanger {

    int NO_COIN = 0;

    Map<Integer, Integer> getUsableCoins();

    int pickCoinFrom(Map<Integer, Integer> usableCoins);

    default int getCoin(Map<Integer, Integer> usableCoins, int remains) {
        int coin = pickCoinFrom(usableCoins);
        decrease(usableCoins, coin);
        if (coin > remains) {
            usableCoins.remove(coin);
            return NO_COIN;
        }
        return coin;
    }

    default CoinCountsDto exchange(Amount amountObj) {
        Map<Integer, Integer> coinCounts = new HashMap<>();
        int amount = amountObj.toInt();

        Map<Integer, Integer> usableCoins = getUsableCoins();
        while (canPickCoin(amount, usableCoins)) {
            int coin = getCoin(usableCoins, amount);
            increase(coinCounts, coin);
            amount -= coin;
        }
        return getCoinCountsDtoFrom(coinCounts);
    }

    private boolean canPickCoin(int remains, Map<Integer, Integer> remainCoins) {
        return (remains > 0) && coinRemains(remainCoins);
    }

    private boolean coinRemains(Map<Integer, Integer> remainCoins) {
        Map<Integer, Integer> coins = new HashMap<>(remainCoins);
        coins.remove(NO_COIN);

        return coins.values().stream()
                .anyMatch(coin -> coin > 0);
    }

    private void increase(Map<Integer, Integer> coinCounts, int coin) {
        int increasedCount = coinCounts.getOrDefault(coin, 0) + 1;
        coinCounts.put(coin, increasedCount);
    }

    private void decrease(Map<Integer, Integer> coinCounts, int coin) {
        int decreasedCount = coinCounts.get(coin) - 1;
        coinCounts.put(coin, decreasedCount);
        if(decreasedCount == 0) {
            coinCounts.remove(coin);
        }
    }

    private CoinCountsDto getCoinCountsDtoFrom(Map<Integer, Integer> coinCounts) {
        Map<Coin, Integer> result = new EnumMap<>(Coin.class);

        coinCounts.remove(NO_COIN);
        coinCounts.forEach((coin, count)
                -> result.put(Coin.getInstanceOf(coin), count));

        return CoinCountsDto.from(result);
    }
}
