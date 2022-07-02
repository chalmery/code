package top.yangcc.facade.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ComplexAggResult implements Serializable {

    private String name;

    private Long count;

    private List<ComplexAggResult> complexAggResults;
}
