package vendingmachine.controller;

import vendingmachine.dto.CoinCountsDto;
import vendingmachine.model.amount.Amount;
import vendingmachine.model.coin.Coin;
import vendingmachine.model.coin.CoinExchanger;
import vendingmachine.model.coin.RandomCoinExchanger;
import vendingmachine.model.coin.RemainCoinExchanger;
import vendingmachine.view.input.InputView;
import vendingmachine.view.input.OwnAmountInputView;
import vendingmachine.view.output.OutputView;

import java.util.Map;

public class RemainsController {

    private final Map<Coin, Integer> vendingMachineCoins;

    public RemainsController() {
        this.vendingMachineCoins = setVendingMachineCoins();
    }

    private static Map<Coin, Integer> setVendingMachineCoins() {
        InputView<Amount> inputView = new OwnAmountInputView();
        Amount amount = inputView.readInput();

        CoinExchanger randomCoinExchanger = new RandomCoinExchanger();
        CoinCountsDto coinCountsDto = randomCoinExchanger.exchange(amount);
        OutputView.printVendingMachineCoins(coinCountsDto);

        return coinCountsDto.getCoinCounts();
    }

    public void printRemains(Amount remains) {
        CoinCountsDto coinCountsDto = exchangeRemains(remains);
        OutputView.printRemainCoins(coinCountsDto);
    }

    private CoinCountsDto exchangeRemains(Amount remains) {
        CoinCountsDto coinCountsDto = CoinCountsDto.from(vendingMachineCoins);
        CoinExchanger remainExchanger = new RemainCoinExchanger(coinCountsDto);
        return remainExchanger.exchange(remains);
    }
}
