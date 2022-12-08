package vendingmachine.dto;

import vendingmachine.model.coin.Coin;
import vendingmachine.valid.CountValidator;

import java.util.EnumMap;
import java.util.Map;

public class CoinCountsDto {

    private final Map<Coin, Integer> coinCounts;

    private CoinCountsDto(Map<Coin, Integer> coinCounts) {
        this.coinCounts = coinCounts;
    }

    public static CoinCountsDto from(Map<Coin, Integer> coinCounts) {
        CountValidator.validate(coinCounts);
        return new CoinCountsDto(coinCounts);
    }

    public Map<Coin, Integer> getCoinCounts() {
        return new EnumMap<>(coinCounts);
    }
}
