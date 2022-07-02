package top.yangcc.facade.dto;

import lombok.EqualsAndHashCode;
import top.yangcc.facade.dto.data.BaseRequest;
import lombok.Data;
import top.yangcc.facade.util.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class EsSearchRequest extends BaseRequest {

    private List<EsFilter> filterList;

    private List<EsQuery> queryList;

    private List<String> returnFields;

    private int from;

    private int size;

    private List<EsSort> sorts;

    private String bizTypeId;

    private String functionScript;

    private Boolean debug = false;

    private String requestId;

    private List<CustomScript> customScripts;

    private List<EsAgg> aggs;

    private String source;


}
