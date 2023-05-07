package top.yangcc.sso.metadata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.sso.service.dto.base.FieldPair;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class BizEnumDTO extends MetaDataDTO {

    private String name;

    private String code;

    private List<FieldPair> value;
}
