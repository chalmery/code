package top.yangcc.sso.dto.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.sso.module.PageRequest;


@EqualsAndHashCode(callSuper = true)
@Data
public class DeptListParam extends PageRequest {


    @Schema(description = "id搜索")
    private Long id;

    @Schema(description = "部门Code模糊搜索")
    private String deptCode;

    @Schema(description = "部门Name模糊搜索")
    private String depName;

    @Schema(description = "部门EnName模糊搜索")
    private String deptEnName;

    @Schema(description = "父级部门Code模糊搜索")
    private Integer parentCode;

}
