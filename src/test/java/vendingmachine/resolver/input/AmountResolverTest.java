package vendingmachine.resolver.input;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import vendingmachine.model.amount.Amount;
import vendingmachine.util.message.error.AmountErrorMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class AmountResolverTest {

    private static final InputResolver<Amount> inputResolver = new AmountResolver();

    @DisplayName("입력된 문자열을 Amount 객체로 변환할 때")
    @Nested
    class ResolveTest {
        @DisplayName("유효한 입력이면 Amount 객체를 반환한다")
        @Nested
        class Valid {
            @ParameterizedTest
            @ValueSource(strings = {"0", "10", "1500"})
            void 객체를_반환한다(String input) {
                Amount expected = Amount.of(Integer.parseInt(input));

                Amount actual = inputResolver.resolve(input);

                assertThat(actual.toInt()).isEqualTo(expected.toInt());
            }
        }


        @DisplayName("유효하지 않은 입력이면 예외가 발생한다")
        @Nested
        class Invalid {
            @DisplayName("정수로 변환할 수 없는 경우")
            @Nested
            class NotParsableToInt {
                @ParameterizedTest
                @ValueSource(strings = {"", "100d", "9999999999999"})
                void IllegalArgumentException이_발생하고_에러_메시지를_출력한다(String input) {
                    assertThatIllegalArgumentException()
                            .isThrownBy(() -> inputResolver.resolve(input))
                            .withMessage(AmountErrorMessage.INVALID_RANGE.fullMessage());
                }
            }

            @DisplayName("유효하지 않는 숫자인 경우")
            @Nested
            class InvalidNumber {
                @Test
                void 음수_입력시_IllegalArgumentException이_발생하고_에러_메시지를_출력한다() {
                    assertThatIllegalArgumentException()
                            .isThrownBy(() -> inputResolver.resolve("-100"))
                            .withMessage(AmountErrorMessage.INVALID_RANGE.fullMessage());
                }

                @Test
                void _15원_입력시_IllegalArgumentException이_발생하고_에러_메시지를_출력한다() {
                    assertThatIllegalArgumentException()
                            .isThrownBy(() -> inputResolver.resolve("15"))
                            .withMessage(AmountErrorMessage.INVALID_UNIT.fullMessage());
                }
            }
        }
    }
}
