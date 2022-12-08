package vendingmachine.view.input;

import vendingmachine.dto.ProductCountsDto;
import vendingmachine.resolver.input.InputResolver;
import vendingmachine.resolver.input.MultipleProductResolver;

public class ProductCountsInputView implements InputView<ProductCountsDto> {

    @Override
    public void printMessage() {
        String message = InputMessage.PRODUCT_INFO.message;
        System.out.println(message);
    }

    @Override
    public ProductCountsDto resolve(String input) {
        InputResolver<ProductCountsDto> inputResolver = new MultipleProductResolver();
        return inputResolver.resolve(input);
    }

}
