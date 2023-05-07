package top.yangcc.sso.metadata.entity;

import lombok.Getter;


@Getter
public enum MeteDataTypeEnum {

    BIZ_ENUM(0,"枚举"),
    ;


    private final Integer code;

    private final String desc;

    MeteDataTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MeteDataTypeEnum bgCode(Integer code){
        if (code ==null){
            return null;
        }
        for (MeteDataTypeEnum value : MeteDataTypeEnum.values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
