package top.yangcc.facade.util.agg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.facade.util.EsAgg;

@Data
@EqualsAndHashCode(callSuper = true)
public class DateHistogramAgg extends EsAgg {

    private String field;

    private String boundStart;

    private String boundEnd;

    private String format;

}
