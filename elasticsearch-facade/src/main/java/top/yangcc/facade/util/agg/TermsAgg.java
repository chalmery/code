package top.yangcc.facade.util.agg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.facade.util.EsAgg;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TermsAgg extends EsAgg {

    private String field;

    private Integer size;

    private List<Object> incluedValues;

    private List<Object> excluedValues;

    private HavingSelector havingSelector;

    private TopHitAgg topHitAgg;

    private List<SumAgg> sumAggs;

    private TermsAgg termsAgg;
}
