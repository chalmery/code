package top.yangcc.facade.result.Data;

public enum ResultCode {

    SUCCESS(0,"success"),
    EXCEPTION(200,"error"),
    ;


    public final Integer code;

    public final String message;

    ResultCode(Integer code,String msg){
        this.code = code;
        this.message = msg;
    }

}
