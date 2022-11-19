package top.yangcc.common.result;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(0,"successful"),
    ERROR(2000,"error"),
    ;
    public final Integer code;
    public final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
