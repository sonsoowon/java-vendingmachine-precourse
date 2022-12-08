package vendingmachine.model.product;

import vendingmachine.model.amount.Amount;
import vendingmachine.valid.ProductValidator;

import java.util.Objects;

public class Product {
    private final String name;
    private final Amount price;

    private Product(String name, Amount price) {
        this.name = name;
        this.price = price;
    }

    public static Product of(String name, Amount price) throws IllegalArgumentException {
        ProductValidator.validate(name, price);
        return new Product(name, price);
    }

    public Amount getPrice() {
        return price;
    }

    public boolean hasName(String name) {
        return name.equals(this.name);
    }

    public boolean canPurchaseWith(Amount amount) {
        return !price.isBiggerThan(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Product) {
            Product other = (Product) o;
            return this.name.equals(other.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name + price;
    }
}
