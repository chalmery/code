package top.yangcc.common.result.module;

import lombok.Data;

import java.io.Serializable;


@Data
public class PageRequest implements Serializable {

    private Integer page = 1;

    private Integer limit = 10;
}
