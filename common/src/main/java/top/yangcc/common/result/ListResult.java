package top.yangcc.common.result;

import lombok.Data;

import java.util.List;

@Data
public class ListResult<T> extends BaseResult {

    private List<T> data;

    private Long count;

    public static <T> ListResult<T> buildSuccess(List<T> data){
        ListResult<T> result = new ListResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> ListResult<T> buildSuccess(List<T> data,Long count){
        ListResult<T> result = new ListResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setSuccess(true);
        result.setData(data);
        result.setCount(count);
        return result;
    }

    public static <T> ListResult<T> buildError(List<T> data){
        ListResult<T> result = new ListResult<>();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMessage(ResultCode.ERROR.getMessage());
        result.setSuccess(false);
        result.setData(data);
        return result;
    }


    public static <T> ListResult<T> buildError(List<T> data,Long count){
        ListResult<T> result = new ListResult<>();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMessage(ResultCode.ERROR.getMessage());
        result.setSuccess(false);
        result.setData(data);
        result.setCount(count);
        return result;
    }

}
