package top.yangcc.sso.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;


@Data
public class EmpVO implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "工号")
    @NotNull(message = "不能为空")
    private String bizId;

    @Schema(description = "名称")
    @NotNull(message = "不能为空")
    private String name;

    @Schema(description = "昵称")
    @NotNull(message = "不能为空")
    private String nickname;

    @Schema(description = "头像")
    @NotNull(message = "不能为空")
    private String avatar;

    @Schema(description = "状态")
    @NotNull(message = "不能为空")
    private Integer status;

    @Schema(description = "类型")
    @NotNull(message = "不能为空")
    private Integer type;
}
