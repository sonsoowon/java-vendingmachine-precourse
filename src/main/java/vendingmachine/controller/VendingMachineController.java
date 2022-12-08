package vendingmachine.controller;


import vendingmachine.dto.ProductCountsDto;
import vendingmachine.model.amount.Amount;
import vendingmachine.model.product.Product;
import vendingmachine.model.product.Products;
import vendingmachine.view.input.InputView;
import vendingmachine.view.input.ProductCountsInputView;
import vendingmachine.view.input.InsertAmountInputView;
import vendingmachine.view.input.PurchaseProductInputView;
import vendingmachine.view.output.OutputView;

import java.util.Map;

public class VendingMachineController {

    private final Map<Product, Integer> productCounts;
    private final RemainsController remainsController;

    private VendingMachineController() {
        this.remainsController = new RemainsController();
        this.productCounts = setProductCounts();
    }

    private static Map<Product, Integer> setProductCounts() {
        InputView<ProductCountsDto> inputView = new ProductCountsInputView();
        ProductCountsDto productCountsDto = inputView.readInput();

        return productCountsDto.getProductCounts();
    }

    public static void start() {
        VendingMachineController vendingMachineController = new VendingMachineController();
        vendingMachineController.insertAmountAndGetRemains();
    }

    private void insertAmountAndGetRemains() {
        Amount insertedAmount = insertAmount();
        Amount remains = getRemains(insertedAmount);
        remainsController.printRemains(remains);
    }

    private Amount insertAmount() {
        InputView<Amount> inputView = new InsertAmountInputView();
        return inputView.readInput();
    }

    private Amount getRemains(Amount amount) {
        Products purchasableProducts = getPurchasableProducts(amount);

        if (purchasableProducts.isEmpty()) {
            return amount;
        }

        Amount remains = getRemainsAfterPurchase(purchasableProducts, amount);
        return getRemains(remains);
    }

    private Products getPurchasableProducts(Amount amount) {
        OutputView.printInsertAmount(amount.toInt());

        Products products = new Products(productCounts.keySet());
        return products.filterPurchasableWith(amount);
    }

    private Amount getRemainsAfterPurchase(Products products, Amount amount) {
        Product purchasedProduct = getProductToPurchase(products);
        decreaseProductCount(purchasedProduct);
        return decreaseAmount(purchasedProduct, amount);
    }

    private Product getProductToPurchase(Products products) {
        InputView<Product> inputView = new PurchaseProductInputView(products);
        return inputView.readInput();
    }

    private void decreaseProductCount(Product product) {
        int decreasedCount = productCounts.get(product) - 1;
        productCounts.put(product, decreasedCount);
        if (decreasedCount == 0) {
            productCounts.remove(product);
        }
    }

    private Amount decreaseAmount(Product product, Amount amount) {
        Amount payed = product.getPrice();
        int decreasedAmount = amount.toInt() - payed.toInt();
        return Amount.of(decreasedAmount);
    }
}
