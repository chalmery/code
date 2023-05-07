package top.yangcc.sso.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;


@Data
public class EmpVO implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "工号")
    @NotEmpty(message = "bizId不能为空")
    private String bizId;

    @Schema(description = "名称")
    @NotEmpty(message = "name不能为空")
    private String name;

    @Schema(description = "昵称")
    @NotEmpty(message = "nickname不能为空")
    private String nickname;

    @Schema(description = "头像")
    @NotEmpty(message = "avatar不能为空")
    private String avatar;

    @Schema(description = "状态")
    @NotEmpty(message = "status不能为空")
    private Integer status;

    @Schema(description = "类型")
    @NotEmpty(message = "type不能为空")
    private Integer type;
}
