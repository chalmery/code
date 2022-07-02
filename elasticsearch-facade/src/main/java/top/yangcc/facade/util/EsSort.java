package top.yangcc.facade.util;

import lombok.Data;

@Data
public class EsSort {
    private String field = "_sort";

    private Order order;

    public enum Order{
        DESC,
        ASC
    }
}
