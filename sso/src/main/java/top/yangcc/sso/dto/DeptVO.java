package top.yangcc.sso.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;


@Data
public class DeptVO implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "部门Code")
    @NotNull(message = "不能为空")
    private Integer deptCode;

    @Schema(description = "部门Name")
    @NotNull(message = "不能为空")
    private String depName;

    @Schema(description = "部门EnName")
    @NotNull(message = "不能为空")
    private String deptEnName;

    @Schema(description = "父级部门Code")
    private Integer parentCode;

}
