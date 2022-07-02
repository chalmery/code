package top.yangcc.facade.util.agg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.facade.util.EsAgg;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SumAgg extends EsAgg {

    private String field;

    private List<Object> incluedValues;
}
