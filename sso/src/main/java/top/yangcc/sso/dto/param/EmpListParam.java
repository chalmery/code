package top.yangcc.sso.dto.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.sso.module.PageRequest;


@EqualsAndHashCode(callSuper = true)
@Data
public class EmpListParam extends PageRequest {


    @Schema(description = "id搜索")
    private Long id;

    @Schema(description = "工号模糊搜索")
    private String bizId;

    @Schema(description = "name模糊搜索")
    private String name;

    @Schema(description = "nickname模糊搜索")
    private String nickname;

    private Long gmtCreateStart;



}
