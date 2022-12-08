package vendingmachine.view.input;

import vendingmachine.model.amount.Amount;
import vendingmachine.resolver.input.AmountResolver;
import vendingmachine.resolver.input.InputResolver;

public class InsertAmountInputView implements InputView<Amount> {
    @Override
    public void printMessage() {
        String message = InputMessage.INSERT_AMOUNT.message;
        System.out.println(message);
    }

    @Override
    public Amount resolve(String input) {
        InputResolver<Amount> amountInputResolver = new AmountResolver();
        return amountInputResolver.resolve(input);
    }

}
