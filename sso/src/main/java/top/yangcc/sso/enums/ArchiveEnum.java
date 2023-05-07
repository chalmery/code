package top.yangcc.sso.enums;


import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public enum ArchiveEnum implements CodeDescEnum{



    NO(0,"未归档"),

    YES(1,"归档"),

    ;


    private final Integer code;

    private final String desc;

    ArchiveEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ArchiveEnum bgCode(Integer code){
        if (code ==null){
            return null;
        }
        for (ArchiveEnum value : ArchiveEnum.values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
