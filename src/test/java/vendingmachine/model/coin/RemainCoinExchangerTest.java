package vendingmachine.model.coin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import vendingmachine.dto.CoinCountsDto;
import vendingmachine.model.amount.Amount;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RemainCoinExchangerTest {

    @DisplayName("자판기에서 최소한의 동전으로 잔돈을 반환할 때")
    @Nested
    class exchangeTest {
        Map<Coin, Integer> vendingMachineCoins = Map.of(
                Coin.COIN_500, 1,
                Coin.COIN_100, 2,
                Coin.COIN_50, 3
        );

        CoinCountsDto vendingMachineCoinsDto = CoinCountsDto.from(vendingMachineCoins);

        CoinExchanger coinExchanger = new RemainCoinExchanger(vendingMachineCoinsDto);

        @DisplayName("잔액이 동전의 총액보다 크거나 같다면")
        @Nested
        class remainsBiggerThanCoins {
            @DisplayName("동전을 모두 반환한다")
            @Test
            void 자판기가_보유한_동전_개수와_반환된_동전_개수가_같다() {
                Amount remains = Amount.of(900);
                Map<Coin, Integer> actual = coinExchanger.exchange(remains)
                        .getCoinCounts();

                assertThat(actual).isEqualTo(vendingMachineCoins);
            }
        }

        @DisplayName("잔액이 동전의 총액보다 작고")
        @Nested
        class remainsSmallerThanCoins {
            Map<Coin, Integer> expected = Map.of(
                    Coin.COIN_500, 1,
                    Coin.COIN_100, 2
            );

            @DisplayName("동전의 최소 단위로 나누어 떨어진다면 잔액만큼의 동전 반환한다")
            @Test
            void _700원을_모두_동전으로_반환한다() {
                Amount remains = Amount.of(700);

                Map<Coin, Integer> actual = coinExchanger.exchange(remains)
                        .getCoinCounts();

                assertThat(actual).isEqualTo(expected);
            }

            @DisplayName("동전의 최소 단위로 나누어 떨어지지 않는다면 나머지를 제외한 잔액만큼의 동전을 반환한다")
            @Test
            void _720원에서_20원을_제외한_금액만큼의_동전을_반환한다() {
                Amount remains = Amount.of(720);

                Map<Coin, Integer> actual = coinExchanger.exchange(remains)
                        .getCoinCounts();

                assertThat(actual).isEqualTo(expected);
            }
        }

    }

}
