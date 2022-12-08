package vendingmachine.resolver.input;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import vendingmachine.dto.ProductCountsDto;
import vendingmachine.model.amount.Amount;
import vendingmachine.model.product.Product;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class MultipleProductResolverTest {

    InputResolver<ProductCountsDto> inputResolver = new MultipleProductResolver();

    @DisplayName("입력된 상품 정보를 ProductCountsDto 객체로 변환할 때")
    @Nested
    class ResolveTest {
        @DisplayName("유효한 입력이면 ProductCountsDto 객체를 반환한다")
        @Nested
        class Valid {
            @Test
            void 상품_정보에_해당하는_ProductCountsDto를_반환한다() {
                String input = "[콜라,1000,1];[사이다,1500,2]";
                Map<Product, Integer> expected = Map.of(
                        Product.of("콜라", Amount.of(1000)), 1,
                        Product.of("사이다", Amount.of(1500)), 2
                );
                ProductCountsDto expectedDto = ProductCountsDto.from(expected);

                ProductCountsDto actualDto = inputResolver.resolve(input);
                Map<Product, Integer> actual = actualDto.getProductCounts();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @DisplayName("유효하지 않은 입력일 때 예외가 발생한다")
        @Nested
        class Invalid {
            @DisplayName("입력 형식이 잘못된 경우")
            @Nested
            class InvalidPattern {
                @ParameterizedTest
                @ValueSource(strings = {"[콜라,1000,2];", "[콜라,1000,2};;[사이다,1500,1]", "[콜라,1000,2};[사이다,1500,-1]"})
                void IllegalArgumentException이_발생한다(String input) {
                    assertThatIllegalArgumentException()
                            .isThrownBy(() -> inputResolver.resolve(input));
                }
            }

            @DisplayName("수량이 잘못된 경우")
            @Nested
            class InvalidCount {
                @ParameterizedTest
                @ValueSource(strings = {"[콜라,1000,0]", "[콜라,1000,99999999999999999]"})
                void invalidCount_IllegalArgumentException이_발생한다(String input) {
                    assertThatIllegalArgumentException()
                            .isThrownBy(() -> inputResolver.resolve(input));
                }
            }


            @DisplayName("가격이 잘못된 경우")
            @Nested
            class InvalidPrice {
                @ParameterizedTest
                @ValueSource(strings = {"[콜라,10,1]", "[콜라,105,1]", "[콜라,999999999999990,1]"})
                void invalidPrice_IllegalArgumentException이_발생한다(String input) {
                    assertThatIllegalArgumentException()
                            .isThrownBy(() -> inputResolver.resolve(input));
                }
            }

            @DisplayName("중복된 상품명이 존재하는 경우")
            @Test
            void duplicate_IllegalArgumentException이_발생하고_에러_메시지를_출력한다() {
                assertThatIllegalArgumentException()
                        .isThrownBy(() -> inputResolver.resolve("[콜라,1000,1];[콜라,2000,2];[사이다,1500,1]"));
            }
        }
    }
}
