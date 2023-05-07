package top.yangcc.sso.enums;


import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public enum EmpType implements CodeDescEnum{



    SYSTEM(0,"系统"),

    FULL_TIME(1,"正式"),


    INTERN(2,"实习"),

    OD(3,"外包"),
    ;


    private final Integer code;

    private final String desc;

    EmpType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EmpType bgCode(Integer code){
        if (code ==null){
            return null;
        }
        for (EmpType value : EmpType.values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
