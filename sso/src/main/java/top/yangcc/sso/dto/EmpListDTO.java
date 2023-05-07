package top.yangcc.sso.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.yangcc.sso.dto.base.FieldPair;

import java.io.Serializable;


@Data
public class EmpListDTO implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "工号")
    private String bizId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "创建时间")
    private Long gmtCreate;

    @Schema(description = "修改时间")
    private Long gmtModify;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "状态")
    private FieldPair status;

    @Schema(description = "类型")
    private FieldPair type;
}
