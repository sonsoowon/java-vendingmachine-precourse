package vendingmachine.model.coin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import vendingmachine.dto.CoinCountsDto;
import vendingmachine.model.amount.Amount;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomCoinExchangerTest extends CoinExchangerTest {
    private static CoinExchanger coinExchanger = new RandomCoinExchanger();

    @DisplayName("동전을 무작위 생성할 때")
    @Nested
    class exchangeTest {
        @DisplayName("입력한 금액만큼의 동전이 생성된다")
        @ParameterizedTest(name = "{0}원")
        @ValueSource(ints = {0, 450})
        void 입력된_금액과_생성된_동전의_총액이_같다(int amount) {
            CoinCountsDto actual = coinExchanger.exchange(Amount.of(amount));

            int actualSum = sumOfExchangedCoins(actual);

            assertThat(actualSum).isEqualTo(amount);
        }
    }
}
