package top.yangcc.facade.util;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CustomScript {

    private List<EsFilter> filters;

    private String script;

    private Map<String, Object> params;

    private Integer priority;

}
