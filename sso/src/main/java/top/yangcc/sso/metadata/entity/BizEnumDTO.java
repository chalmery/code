package top.yangcc.sso.metadata.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.sso.dto.base.FieldPair;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class BizEnumDTO extends MetaDataDTO {


    @Schema(description = "枚举名称")
    private String name;

    @Schema(description = "枚举Code")
    private String code;

    @Schema(description = "枚举值")
    private List<FieldPair> value;
}
