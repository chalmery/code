package top.yangcc.sso.metadata.converter;


import com.alibaba.fastjson.JSON;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import top.yangcc.sso.dao.dataobject.BizEnumDO;
import top.yangcc.sso.metadata.entity.BizEnumDTO;
import top.yangcc.sso.service.converter.BaseConverter;
import top.yangcc.sso.service.dto.base.FieldPair;

import java.util.List;

@Mapper(imports = {FieldPair.class, JSON.class})
public interface MetaConverter extends BaseConverter {
    MetaConverter INSTANCE = Mappers.getMapper(MetaConverter.class);


    @Named("toDTO")
    @Mappings({
            @Mapping(target = "value", expression = "java(JSON.parseArray(enumDO.getValue(),FieldPair.class))"),
    })
    BizEnumDTO toDTO(BizEnumDO enumDO);


    @IterableMapping(qualifiedByName = "toDTO")
    List<BizEnumDTO> toDTOList(List<BizEnumDO> enumDOS);
}
