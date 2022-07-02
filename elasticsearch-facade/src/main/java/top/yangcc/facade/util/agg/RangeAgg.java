package top.yangcc.facade.util.agg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.facade.util.EsAgg;
import top.yangcc.facade.util.Range;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RangeAgg extends EsAgg {

    private String field;

    private List<Range> ranges = new ArrayList<>();
}
