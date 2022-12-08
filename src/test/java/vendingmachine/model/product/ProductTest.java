package vendingmachine.model.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import vendingmachine.model.amount.Amount;
import vendingmachine.util.message.error.ProductErrorMessage;

import static org.assertj.core.api.Assertions.*;

public class ProductTest {

    @DisplayName("Product 객체를 생성할 때")
    @Nested
    class ConstructorTest {

        String validName = "콜라";
        Amount validPrice = Amount.of(1000);

        @DisplayName("올바른 값을 입력하면 예외가 발생하지 않는다")
        @Nested
        class valid {
            @Test
            void 콜라_1000원_상품를_생성하면_예외가_발생하지_않는다() {
                assertThatNoException()
                        .isThrownBy(() -> Product.of(validName, validPrice));
            }
        }

        @DisplayName("잘못된 값을 입력하면 예외가 발생한다")
        @Nested
        class invalid {
            @DisplayName("빈 문자열을 이름으로 입력하는 경우")
            @Test
            void 이름이_빈문자열인_상품를_생성하면_IllegalArgumentException이_발생한다() {
                assertThatIllegalArgumentException()
                        .isThrownBy(() -> Product.of("", validPrice))
                        .withMessage(ProductErrorMessage.INVALID_NAME.fullMessage());
            }

            @DisplayName("100원보다 작은 가격을 입력하는 경우")
            @Test
            void 가격이_90원인_상품을_생성하면_IllegalArgumentException이_발생한다() {
                assertThatIllegalArgumentException()
                        .isThrownBy(() -> Product.of(validName, Amount.of(90)))
                        .withMessage(ProductErrorMessage.INVALID_PRICE.fullMessage());
            }
        }
    }


    @DisplayName("특정 이름을 가진 상품인지 확인할 때")
    @Nested
    class HasNameTest {

        String name = "콜라";
        Amount price = Amount.of(1000);

        @DisplayName("상품 이름과 같다면 참이다")
        @Test
        void 참을_반환한다() {
            Product product = Product.of("콜라", price);

            boolean actual = product.hasName(name);

            assertThat(actual).isTrue();
        }

        @DisplayName("상품 이름과 다르다면 거짓이다")
        @Test
        void 거짓을_반환한다() {
            Product product = Product.of("사이다", price);

            boolean actual = product.hasName(name);

            assertThat(actual).isFalse();
        }
    }


    @DisplayName("특정 금액으로 구매 가능한 상품인지 판별할 때")
    @Nested
    class CanPurchasWithTest {
        Product product = Product.of("콜라", Amount.of(1000));

        @DisplayName("금액보다 작거나 같은 가격이라면 참이다")
        @Test
        void _1000원짜리_콜라를_구매할_수_있다() {
            Amount target = Amount.of(1500);

            boolean actual = product.canPurchaseWith(target);

            assertThat(actual).isTrue();
        }

        @DisplayName("금액보다 큰 가격이라면 거짓이다")
        @Test
        void _1000원짜리_콜라를_구매할_수_없다() {
            Amount target = Amount.of(900);

            boolean actual = product.canPurchaseWith(target);

            assertThat(actual).isFalse();
        }
    }


    @DisplayName("두 객체의 동일성을 판별할 때")
    @Nested
    class EqualsTest {

        Product product = Product.of("콜라", Amount.of(1000));

        @DisplayName("이름이 같다면 동일한 객체이다")
        @Test
        void 콜라_1000원과_콜라_2000원은_동일하다() {
            Product target = Product.of("콜라", Amount.of(2000));
            assertThat(product).isEqualTo(target);
        }


        @DisplayName("이름이 다르다면 다른 객체이다")
        @Test
        void 콜라_1000원과_사이다_1000원은_다르다() {
            Product target = Product.of("사이다", Amount.of(1000));
            assertThat(product).isNotEqualTo(target);
        }
    }
}
