package vendingmachine.view.output;

import vendingmachine.dto.CoinCountsDto;
import vendingmachine.resolver.output.CoinCountsOutputResolver;

public class OutputView {

    public static void printVendingMachineCoins(CoinCountsDto coinCountsDto) {
        String message = OutputMessage.VENDING_MACHINE_COINS.message;
        System.out.println(message);

        CoinCountsOutputResolver.getInstanceOf(coinCountsDto)
                .outputAllCoins()
                .forEach(System.out::println);
    }

    public static void printInsertAmount(int amount) {
        String messageFormat = OutputMessage.INSERT_AMOUNT.message;
        String message = String.format(messageFormat, amount);
        System.out.println(message);
    }

    public static void printRemainCoins(CoinCountsDto coinCountsDto) {
        String message = OutputMessage.REMAINS.message;
        System.out.println(message);

        CoinCountsOutputResolver.getInstanceOf(coinCountsDto)
                .outputNonZeroCoins()
                .forEach(System.out::println);
    }

    public enum OutputMessage {

        VENDING_MACHINE_COINS("자판기가 보유한 동전"),
        INSERT_AMOUNT("투입 금액: %d원"),
        REMAINS("잔돈");
        public final String message;

        OutputMessage(String message) {
            this.message = message;
        }

    }
}
