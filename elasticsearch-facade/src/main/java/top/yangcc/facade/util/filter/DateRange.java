package top.yangcc.facade.util.filter;

import lombok.Data;

@Data
public class DateRange {

    private String name;

    private Long from;

    private Long to;

    private Boolean includeFrom = true;

    private Boolean includeTo = true;

    private Long greaterThen;

    private Long lessThen;
}
