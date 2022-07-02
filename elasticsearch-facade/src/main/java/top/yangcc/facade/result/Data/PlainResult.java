package top.yangcc.facade.result.Data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlainResult<T> extends BaseResult {

    private T data;

    public static <T> PlainResult<T> buildSucResult(T data) {
        PlainResult<T> result = new PlainResult<>();
        result.setCode(ResultCode.SUCCESS.code);
        result.setMessage(ResultCode.SUCCESS.message);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> PlainResult<T> buildErrResult(Integer code, String msg) {
        PlainResult<T> result = new PlainResult<>();
        result.setCode(code);
        result.setMessage(msg);
        result.setSuccess(false);
        return result;
    }
}
