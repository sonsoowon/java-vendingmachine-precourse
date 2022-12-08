package vendingmachine.view.input;

import vendingmachine.model.amount.Amount;
import vendingmachine.resolver.input.AmountResolver;
import vendingmachine.resolver.input.InputResolver;


public class OwnAmountInputView implements InputView<Amount> {

    @Override
    public void printMessage() {
        String message = InputMessage.OWN_AMOUNT.message;
        System.out.println(message);
    }

    @Override
    public Amount resolve(String input) {
        InputResolver<Amount> inputResolver = new AmountResolver();
        return inputResolver.resolve(input);
    }
}
