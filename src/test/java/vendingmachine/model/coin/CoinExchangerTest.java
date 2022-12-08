package vendingmachine.model.coin;

import vendingmachine.dto.CoinCountsDto;

import java.util.ArrayList;
import java.util.List;

public class CoinExchangerTest {
    public static int sumOfExchangedCoins(CoinCountsDto exchangedCoins) {
        List<Integer> exchangedAmounts = new ArrayList<>();
        exchangedCoins.getCoinCounts()
                .forEach((coin, count)
                        -> exchangedAmounts.add(coin.getAmount() * count));

        return exchangedAmounts.stream()
                .reduce(Integer::sum)
                .orElse(0);
    }


}
