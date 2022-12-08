package vendingmachine.model.coin;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;
import java.util.stream.Collectors;

public class RandomCoinExchanger implements CoinExchanger {

    public RandomCoinExchanger() {
    }

    @Override
    public Map<Integer, Integer> getUsableCoins() {
        Map<Integer, Integer> usableCoins = new HashMap<>();

        Arrays.stream(Coin.values())
                .map(Coin::getAmount)
                .forEach(coin -> usableCoins.put(coin, Integer.MAX_VALUE));

        return usableCoins;
    }

    @Override
    public int pickCoinFrom(Map<Integer, Integer> usableCoins) {
        List<Integer> coins = usableCoins.keySet().stream()
                .collect(Collectors.toUnmodifiableList());
        return Randoms.pickNumberInList(coins);
    }

}
