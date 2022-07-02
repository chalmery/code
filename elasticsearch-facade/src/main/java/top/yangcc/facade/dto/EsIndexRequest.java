package top.yangcc.facade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsIndexRequest {

    private String bizTypeId;

    private String indexName;

    private String indexType;

    private String docId;

    private Map<String,Object> issue;
}
