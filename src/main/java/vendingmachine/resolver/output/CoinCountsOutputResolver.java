package vendingmachine.resolver.output;

import vendingmachine.dto.CoinCountsDto;
import vendingmachine.model.coin.Coin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CoinCountsOutputResolver {

    private static final String OUTPUT_FORMAT = "%d원 - %d개";
    private static final int DEFAULT_COUNT = 0;

    private final Map<Coin, Integer> coinCounts;

    private CoinCountsOutputResolver(Map<Coin, Integer> coinCounts) {
        this.coinCounts = coinCounts;
    }

    public static CoinCountsOutputResolver getInstanceOf(CoinCountsDto coinCountsDto) {
        Map<Coin, Integer> coinCounts = coinCountsDto.getCoinCounts();
        return new CoinCountsOutputResolver(coinCounts);
    }

    public List<String> outputAllCoins() {
        return outputCoins(Coin.sortedValuesInDescendingOrder());
    }

    public List<String> outputNonZeroCoins() {
        List<Coin> coinsMoreThanZero = Coin.sortedValuesInDescendingOrder().stream()
                .filter(this::countMoreThanZero)
                .collect(Collectors.toUnmodifiableList());

        return outputCoins(coinsMoreThanZero);
    }

    private List<String> outputCoins(List<Coin> coins) {
        List<String> outputLines = new ArrayList<>();
        for(Coin coin : coins) {
            int unit = coin.getAmount();
            int count = coinCounts.getOrDefault(coin, DEFAULT_COUNT);

            String line = String.format(OUTPUT_FORMAT, unit, count);
            outputLines.add(line);
        }
        return outputLines;
    }

    private boolean countMoreThanZero(Coin coin) {
        return coinCounts.getOrDefault(coin, DEFAULT_COUNT) > DEFAULT_COUNT;
    }

}
