package top.yangcc.common.enums;



@SuppressWarnings("unused")
public enum AppEnum {
    AND("&"),

    DOUBLE_AND("&&"),

    OR("|"),

    DOUBLE_OR("||"),

    SLASH("/"),


    LEFT_BRACKET("("),

    RIGHT_BRACKET(")"),


    ;

    private final String code;

    AppEnum(String code) {
        this.code = code;
    }

}
