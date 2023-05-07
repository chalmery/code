package top.yangcc.sso.enums;


import lombok.Getter;

@SuppressWarnings("unused")
@Getter
public enum EmpStatus implements CodeDescEnum{



    WORKING(0,"在职"),
    RESIGN(1,"离职");


    private final Integer code;

    private final String desc;

    EmpStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EmpStatus bgCode(Integer code){
        if (code ==null){
            return null;
        }
        for (EmpStatus value : EmpStatus.values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
