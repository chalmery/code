package top.yangcc.facade.util.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.facade.util.EsFilter;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotInFilter extends EsFilter {

    private List<String> value;
}
