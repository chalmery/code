package top.yangcc.facade.util;

import lombok.Data;

@Data
public class EsQuery {

    private String name;

    private String value;

    private Float boost;

    private Operator operator;


    public enum Operator{
        AND,
        OR
    }
}
