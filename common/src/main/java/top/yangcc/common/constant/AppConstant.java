package top.yangcc.common.constant;

public enum AppConstant {
    AND("&"),

    DOUBLE_AND("&&"),

    OR("|"),

    DOUBLE_OR("||"),

    SLASH("/"),


    LEFT_BRACKET("("),

    RIGHT_BRACKET(")"),


    ;


    private final String code;

    AppConstant(String code) {
        this.code = code;
    }

}
