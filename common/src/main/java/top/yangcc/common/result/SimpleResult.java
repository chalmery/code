package top.yangcc.common.result;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@SuppressWarnings("unused")
public class SimpleResult<T> extends BaseResult {

    private T data;


    public static <T> SimpleResult<T> buildSuccess(T data){
        SimpleResult<T> result = new SimpleResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> SimpleResult<T> buildSuccess(){
        SimpleResult<T> result = new SimpleResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setSuccess(true);
        return result;
    }

    public static <T> SimpleResult<T> buildError(){
        SimpleResult<T> result = new SimpleResult<>();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMessage(ResultCode.ERROR.getMessage());
        result.setSuccess(false);
        return result;
    }


    public static <T> SimpleResult<T> buildError(String message){
        SimpleResult<T> result = new SimpleResult<>();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }

}
