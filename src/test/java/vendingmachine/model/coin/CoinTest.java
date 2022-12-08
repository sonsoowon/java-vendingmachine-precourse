package vendingmachine.model.coin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CoinTest {

    @DisplayName("Coin 인스턴스를 금액 기준으로 내림차순 정렬한다")
    @Test
    void sortedValuesTest_500원부터_10원까지_순서대로_나열된_리스트를_반환한다() {
        List<Coin> expected = List.of(Coin.COIN_500, Coin.COIN_100, Coin.COIN_50, Coin.COIN_10);

        List<Coin> actual = Coin.sortedValuesInDescendingOrder();

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("특정 금액에 해당하는 Coin 객체를 조회할 때")
    @Nested
    class GetInstanceOfTest {
        @DisplayName("객체가 존재한다면 해당 객체를 반환한다")
        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        class Valid {
            @ParameterizedTest(name = "{0}원 - {1}")
            @MethodSource("validTestSource")
            void _10원부터_500원까지_차례로_조회한다(int amount, Coin expected) {
                Coin actual = Coin.getInstanceOf(amount);

                assertThat(actual).isEqualTo(expected);
            }

            Stream<Arguments> validTestSource() {
                return Stream.of(
                        Arguments.of(10, Coin.COIN_10),
                        Arguments.of(50, Coin.COIN_50),
                        Arguments.of(100, Coin.COIN_100),
                        Arguments.of(500, Coin.COIN_500)
                );
            }
        }


        @DisplayName("해당 금액의 객체가 존재하지 않는다면 예외가 발생한다")
        @Nested
        class Invalid {
            @Test
            void _5원을_조회할때_NoSuchElementException이_발생한다() {
                assertThatExceptionOfType(NoSuchElementException.class)
                        .isThrownBy(() -> Coin.getInstanceOf(5));
            }
        }
    }

}
