package top.yangcc.sso.module;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo implements Serializable {

    private Integer page;

    private Integer limit;
}
