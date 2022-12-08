package vendingmachine.model.product;

import vendingmachine.model.amount.Amount;
import vendingmachine.util.message.error.ProductErrorMessage;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Products {

    private static final Supplier<IllegalArgumentException> PRODUCT_NOT_FOUND_EXCEPTION
            = () -> new IllegalArgumentException(
            ProductErrorMessage.PRODUCT_NOT_FOUND.fullMessage());

    private final Set<Product> products;

    public Products(Set<Product> products) {
        this.products = products;
    }

    public Products filterPurchasableWith(Amount amount) {
        Set<Product> filteredProducts = products.stream()
                .filter(product -> product.canPurchaseWith(amount))
                .collect(Collectors.toSet());

        return new Products(filteredProducts);
    }

    public Product findProductByName(String name) {
        return products.stream()
                .filter(product -> product.hasName(name))
                .findFirst()
                .orElseThrow(PRODUCT_NOT_FOUND_EXCEPTION);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public Set<Product> getProducts() {
        return new HashSet<>(products);
    }

    @Override
    public String toString() {
        return products.toString();
    }
}
