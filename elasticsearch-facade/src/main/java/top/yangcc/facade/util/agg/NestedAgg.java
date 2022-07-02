package top.yangcc.facade.util.agg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.facade.util.EsAgg;

@Data
@EqualsAndHashCode(callSuper = true)
public class NestedAgg extends EsAgg {
    private String path;

    private DateHistogramAgg dateAgg;
}
