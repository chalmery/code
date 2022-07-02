package top.yangcc.facade.util.agg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.facade.util.EsAgg;
import top.yangcc.facade.util.EsSort;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TopHitAgg extends EsAgg {

    private Integer size;

    private List<EsSort> sort;

    private List<String> returnFields;
}
