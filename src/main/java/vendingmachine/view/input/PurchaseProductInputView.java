package vendingmachine.view.input;

import vendingmachine.model.product.Product;
import vendingmachine.model.product.Products;

public class PurchaseProductInputView implements InputView<Product> {

    private final Products products;

    public PurchaseProductInputView(Products products) {
        this.products = products;
    }

    @Override
    public void printMessage() {
        String message = InputMessage.PURCHASE_PRODUCT.message;
        System.out.println(message);
    }

    @Override
    public Product resolve(String input) {
        return products.findProductByName(input);
    }
}
