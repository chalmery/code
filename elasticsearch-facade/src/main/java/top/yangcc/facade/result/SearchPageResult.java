package top.yangcc.facade.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.facade.result.Data.PageResult;
import top.yangcc.facade.result.Data.ResultCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchPageResult<T> extends PageResult<T> {

    private Map<String, Map<String, Long>> aggResult;
    private Map<String, List<T>> topHitAggDocs;

    private List<ComplexAggResult> complexAggResults;

    public SearchPageResult(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }

    public static <T> SearchPageResult<T> buildSuccessPageResult(Integer pageSize,
                                                                 Integer pageNumber,
                                                                 List<T> target) {
        SearchPageResult<T> pageResult = new SearchPageResult<>(pageNumber, pageSize);
        pageResult.setData(target);
        pageResult.setSuccess(true);
        pageResult.setCode(ResultCode.SUCCESS.code);
        pageResult.setTotal(0L);
        return pageResult;
    }

    public static <T> SearchPageResult<T> buildFailPageResult(Integer pageSize, Integer pageNumber, String msg) {
        SearchPageResult<T> pageResult = new SearchPageResult<>(pageNumber, pageSize);
        pageResult.setData(new ArrayList<>());
        pageResult.setSuccess(false);
        pageResult.setCode(ResultCode.EXCEPTION.code);
        pageResult.setMessage(msg);
        pageResult.setTotal(0L);
        return pageResult;
    }

}
