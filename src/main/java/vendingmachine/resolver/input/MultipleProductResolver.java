package vendingmachine.resolver.input;

import vendingmachine.dto.ProductCountsDto;
import vendingmachine.model.amount.Amount;
import vendingmachine.model.product.Product;
import vendingmachine.util.consts.ProductIndex;
import vendingmachine.util.consts.PublicFormat;
import vendingmachine.util.message.error.ProductErrorMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleProductResolver implements InputResolver<ProductCountsDto> {

    private static final InputResolver<Amount> amountInputResolver = new AmountResolver();
    private static final InputResolver<Integer> countInputResolver = new CountResolver();

    private final Map<Product, Integer> productCounts = new HashMap<>();

    public MultipleProductResolver() {
    }

    @Override
    public ProductCountsDto resolve(String input) {
        validate(input);
        setProductCounts(input);

        return ProductCountsDto.from(productCounts);
    }

    private void validate(String input) {
        checkPattern(input);
        checkDuplicate(input);
    }

    private void checkDuplicate(String input) {
        if (!hasNoDuplicate(input)) {
            String message = ProductErrorMessage.DUPLICATE_NAME.fullMessage();
            throw new IllegalArgumentException(message);
        }
    }

    private boolean hasNoDuplicate(String input) {
        List<String> products = decodeMultipleInput(input);
        int distinctSize = (int) products.stream()
                .map(this::decodeProductDetails)
                .map(product -> product.get(ProductIndex.NAME))
                .distinct().count();

        return products.size() == distinctSize;
    }

    private static void checkPattern(String input) {
        if (!input.matches(PublicFormat.PATTERN)) {
            String message = ProductErrorMessage.INVALID_FORMAT.fullMessage();
            throw new IllegalArgumentException(message);
        }
    }

    private void setProductCounts(String input) {
        List<String> productsToDecode = decodeMultipleInput(input);
        productsToDecode.forEach(this::putProduct);
    }

    private List<String> decodeMultipleInput(String input) {
        return List.of(input.split(PublicFormat.OUTER_SEPARATOR));
    }

    private void putProduct(String input) {
        List<String> productDetails = decodeProductDetails(input);

        String name = getNameFrom(productDetails);
        Amount price = getPriceFrom(productDetails);
        int count = getCountFrom(productDetails);

        Product product = Product.of(name, price);
        productCounts.put(product, count);
    }

    private List<String> decodeProductDetails(String input) {
        return List.of(input.replaceAll(PublicFormat.BOX, PublicFormat.EMPTY)
                .split(PublicFormat.INNER_SEPARATOR));
    }

    private String getNameFrom(List<String> productDetails) {
        return productDetails.get(ProductIndex.NAME);
    }

    private Amount getPriceFrom(List<String> productDetails) {
        String price = productDetails.get(ProductIndex.PRICE);
        return amountInputResolver.resolve(price);
    }

    private Integer getCountFrom(List<String> productDetails) {
        String count = productDetails.get(ProductIndex.COUNT);
        return countInputResolver.resolve(count);
    }
}
