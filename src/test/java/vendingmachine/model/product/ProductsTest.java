package vendingmachine.model.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import vendingmachine.model.amount.Amount;
import vendingmachine.util.message.error.ProductErrorMessage;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ProductsTest {

    Products products = new Products(Set.of(
            Product.of("사이다", Amount.of(900)),
            Product.of("콜라", Amount.of(1000)),
            Product.of("환타", Amount.of(1500))
    ));

    @DisplayName("특정 금액으로 구매 가능한 상품 목록을 분류한다")
    @Test
    void filterPurchasableWith_1000원보다_작거나_같은_가격의_상품_목록_객체를_반환한다() {
        Set<Product> expected = Set.of(
                Product.of("사이다", Amount.of(900)),
                Product.of("콜라", Amount.of(1000))
        );

        Set<Product> actual = products.filterPurchasableWith(Amount.of(1000))
                        .getProducts();

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("상품 목록에서 특정 이름의 상품을 찾을 때")
    @Nested
    class FindProductByNameTest {
        @DisplayName("해당 상품이 존재한다면 상품 객체를 반환한다")
        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        class productExists {
            @ParameterizedTest
            @MethodSource("findProductTestSource")
            void 입력된_문자열을_이름으로_갖는_객체를_반환한다(String name, Product expected) {
                Product actual = products.findProductByName(name);

                assertThat(actual).isEqualTo(expected);
            }

            Stream<Arguments> findProductTestSource() {
                return Stream.of(
                        Arguments.of("사이다", Product.of("사이다", Amount.of(900))),
                        Arguments.of("콜라", Product.of("콜라", Amount.of(1000))),
                        Arguments.of("환타", Product.of("환타", Amount.of(1500)))
                );
            }
        }


        @DisplayName("해당 상품이 존재하지 않는다면 예외가 발생한다")
        @Test
        void IllegalArgumentException이_발생하고_에러_메시지를_출력한다() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> products.findProductByName("오란씨"))
                    .withMessage(ProductErrorMessage.PRODUCT_NOT_FOUND.fullMessage());
        }
    }


    @DisplayName("상품 목록이 비었는지 판별할 때")
    @Nested
    class IsEmptyTest {
        @DisplayName("products 필드가 비었다면 참이다")
        @Test
        void 참을_반환한다() {
            Products products = new Products(Set.of());

            boolean actual = products.isEmpty();

            assertThat(actual).isTrue();
        }

        @DisplayName("products 필드가 비어있지 않다면 거짓이다")
        @Test
        void 거짓을_반환한다() {
            Products products = new Products(Set.of(
                    Product.of("콜라", Amount.of(1000))
            ));

            boolean actual = products.isEmpty();

            assertThat(actual).isFalse();
        }
    }
}
