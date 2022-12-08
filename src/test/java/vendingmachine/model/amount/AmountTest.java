package vendingmachine.model.amount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import vendingmachine.util.message.error.AmountErrorMessage;

import static org.assertj.core.api.Assertions.*;

public class AmountTest {
    @DisplayName("Amount 객체를 생성할 때")
    @Nested
    class constructorTest {
        @DisplayName("올바른 숫자를 입력하면 예외가 발생하지 않는다")
        @Nested
        class validNumber {
            @ParameterizedTest(name = "{0}원")
            @ValueSource(ints = {0, 150})
            void _10으로_나누어_떨어지는_0_이상의_정수에_대해_예외가_발생하지_않는다(int amount) {
                assertThatNoException()
                        .isThrownBy(() -> Amount.of(amount));
            }
        }

        @DisplayName("잘못된 숫자를 입력하면 예외가 발생한다")
        @Nested
        class invalidNumber {
            @DisplayName("10으로 나누어 떨어지지 않는 경우")
            @Test
            void _5를_입력하면_IllegalArgumentException이_발생하고_에러_메시지를_출력한다() {
                assertThatIllegalArgumentException()
                        .isThrownBy(() -> Amount.of(5))
                        .withMessage(AmountErrorMessage.INVALID_UNIT.fullMessage());
            }

            @DisplayName("음수인 경우")
            @Test
            void _음수를_입력하면_IllegalArgumentException이_발생하고_에러_메시지를_출력한다() {
                assertThatIllegalArgumentException()
                        .isThrownBy(() -> Amount.of(-100))
                        .withMessage(AmountErrorMessage.INVALID_RANGE.fullMessage());
            }
        }
    }


    @DisplayName("Amount 객체 A가 B보다 작은 지 확인할 때")
    @Nested
    class IsBiggerThanTest {
        Amount a = Amount.of(100);

        @DisplayName("A의 금액이 B의 금액보다 크다면 참이다")
        @Test
        void 참을_반환한다() {
            Amount b = Amount.of(90);
            assertThat(a.isBiggerThan(b)).isTrue();
        }

        @DisplayName("A의 금액이 B의 금액보다 작거나 같다면 거짓이다")
        @Test
        void 거짓을_반환한다() {
            Amount b = Amount.of(150);
            assertThat(a.isBiggerThan(b)).isFalse();
        }
    }
}
