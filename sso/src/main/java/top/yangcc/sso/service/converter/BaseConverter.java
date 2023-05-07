package top.yangcc.sso.service.converter;


import org.mapstruct.Mapper;
import top.yangcc.sso.dto.base.FieldPair;
import top.yangcc.sso.enums.CodeDescEnum;

import java.util.Objects;

@Mapper
public interface BaseConverter {

    default FieldPair parseEnum(CodeDescEnum descEnum) {
        if (Objects.isNull(descEnum)) {
            return null;
        }
        return new FieldPair(descEnum.getDesc(), descEnum.getCode());
    }
}
