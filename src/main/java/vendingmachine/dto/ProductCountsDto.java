package vendingmachine.dto;

import vendingmachine.model.product.Product;
import vendingmachine.valid.CountValidator;

import java.util.HashMap;
import java.util.Map;

public class ProductCountsDto {
    private final Map<Product, Integer> productCounts;

    private ProductCountsDto(Map<Product, Integer> productCounts) {
        this.productCounts = productCounts;
    }

    public static ProductCountsDto from(Map<Product, Integer> productCounts) {
        CountValidator.validate(productCounts);
        return new ProductCountsDto(productCounts);
    }

    public Map<Product, Integer> getProductCounts() {
        return new HashMap<>(productCounts);
    }


}
