package top.yangcc.facade.util.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.facade.util.EsFilter;


@Data
@EqualsAndHashCode(callSuper = true)
public class MatchFilter extends EsFilter {

    private String text;

    private String minimumShouldMatch;

    private Operator operator;

    public enum Operator{
        AND,
        OR
    }
}
