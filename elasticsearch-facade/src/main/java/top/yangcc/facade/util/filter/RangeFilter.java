package top.yangcc.facade.util.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.facade.util.EsFilter;

@Data
@EqualsAndHashCode(callSuper = true)
public class RangeFilter extends EsFilter {

    private DateRange dateRange;
}
