package top.yangcc.facade.result;

import lombok.Data;

import java.util.Map;

@Data
public class SearchResultValue {
    private Map<String, Object> docFields;

    private String id;

    private String _id;

    private Float score;
}
