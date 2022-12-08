package vendingmachine.util.message.error;

import vendingmachine.util.consts.ProductConst;
import vendingmachine.util.message.error.ErrorMessage;

public enum ProductErrorMessage implements ErrorMessage {

    INVALID_FORMAT("알맞는 형식의 상품 정보를 입력해야 합니다."),
    INVALID_NAME("상품명은 한 글자 이상이어야 합니다."),
    INVALID_PRICE(String.format("가격은 %d원 이상이어야 합니다.", ProductConst.MIN_PRICE)),
    DUPLICATE_NAME("상품명은 중복되지 않아야 합니다."),
    PRODUCT_NOT_FOUND("구매할 수 없는 상품입니다.");

    private final String message;

    ProductErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String fullMessage() {
        return ErrorMessage.PREDICATE + message;
    }
}
