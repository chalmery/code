package top.yangcc.sso.module;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


@Data
public class PageRequest implements Serializable {


    @Schema(description = "当前页",defaultValue = "1")
    private Integer page = 1;

    @Schema(description = "每页显示多少条",defaultValue = "10")
    private Integer limit = 10;
}
