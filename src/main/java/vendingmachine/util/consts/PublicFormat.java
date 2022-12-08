package vendingmachine.util.consts;

public class PublicFormat {
    public static final String PATTERN = "(\\[[가-힣a-zA-Z]+,\\d+,\\d+])(;\\[[가-힣a-zA-Z]+,\\d+,\\d+])*";

    public static final String OUTER_SEPARATOR = ";";

    public static final String BOX = "[\\[|\\]]";
    public static final String INNER_SEPARATOR = ",";
    public static final String EMPTY = "";

    private PublicFormat() {
    }
}
