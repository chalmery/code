package top.yangcc.facade.util.agg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.facade.util.EsAgg;

@Data
@EqualsAndHashCode(callSuper = true)
public class HavingSelector extends EsAgg {

    private Integer value;

    private Condition condition;

    public enum Condition{
        GT(" > "),
        LT("<"),
        GTE(">="),
        LTE("<="),
        EQ("=="),
        ;

        public final String expr;

        Condition(String expr) {
            this.expr = expr;
        }
    }
}
