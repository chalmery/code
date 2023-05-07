package top.yangcc.sso.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


@Data
public class DeptDTO implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "创建时间")
    private Long gmtCreate;

    @Schema(description = "修改时间")
    private Long gmtModify;

    @Schema(description = "部门Code")
    private Integer deptCode;

    @Schema(description = "部门Name")
    private String depName;

    @Schema(description = "部门EnName")
    private String deptEnName;

    @Schema(description = "父级部门Code")
    private Integer parentCode;

}
