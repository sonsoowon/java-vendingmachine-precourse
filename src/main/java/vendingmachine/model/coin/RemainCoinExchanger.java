package vendingmachine.model.coin;

import vendingmachine.dto.CoinCountsDto;

import java.util.HashMap;
import java.util.Map;

public class RemainCoinExchanger implements CoinExchanger {
    private final Map<Coin, Integer> vendingMachineCoins;

    public RemainCoinExchanger(CoinCountsDto coinCountsDto) {
        this.vendingMachineCoins = coinCountsDto.getCoinCounts();
    }

    @Override
    public Map<Integer, Integer> getUsableCoins() {
        Map<Integer, Integer> usableCoins = new HashMap<>();
        vendingMachineCoins.forEach((coin, count)
                -> usableCoins.put(coin.getAmount(), count));
        return usableCoins;
    }

    @Override
    public int pickCoinFrom(Map<Integer, Integer> usableCoins) {
        return usableCoins.keySet().stream()
                .max(Integer::compareTo)
                .orElseThrow();
    }
}
