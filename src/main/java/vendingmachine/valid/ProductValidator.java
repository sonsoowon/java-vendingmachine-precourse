package vendingmachine.valid;

import vendingmachine.model.amount.Amount;
import vendingmachine.util.consts.ProductConst;
import vendingmachine.util.message.error.ProductErrorMessage;

public class ProductValidator {

    private ProductValidator() {}

    public static void validate(String name, Amount price) {
        checkName(name);
        checkPrice(price);
    }

    private static void checkName(String name) {
        if (invalidName(name)) {
            String message = ProductErrorMessage.INVALID_NAME.fullMessage();
            throw new IllegalArgumentException(message);
        }
    }

    private static boolean invalidName(String name) {
        return name.length() < ProductConst.MIN_NAME_LENGTH;
    }

    private static void checkPrice(Amount price) {
        if(invalidPrice(price)) {
            String message = ProductErrorMessage.INVALID_PRICE.fullMessage();
            throw new IllegalArgumentException(message);
        }
    }

    private static boolean invalidPrice(Amount price) {
        Amount minPrice = Amount.of(ProductConst.MIN_PRICE);
        return price.toInt() < minPrice.toInt();
    }
}
