package top.yangcc.sso.service.converter;


import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import top.yangcc.sso.dao.dataobject.EmpDO;
import top.yangcc.sso.dto.EmpDTO;
import top.yangcc.sso.dto.EmpListDTO;
import top.yangcc.sso.dto.base.FieldPair;
import top.yangcc.sso.enums.EmpStatus;
import top.yangcc.sso.enums.EmpType;

import java.util.List;

@Mapper(imports = {FieldPair.class, EmpStatus.class, EmpType.class})
public interface EmpConverter extends BaseConverter{
    EmpConverter INSTANCE = Mappers.getMapper(EmpConverter.class);


    @Named("toListDTO")
    @Mappings({
            @Mapping(target = "gmtCreate",source = "gmtCreate.time"),
            @Mapping(target = "gmtModify",source = "gmtModify.time"),
            @Mapping(target = "status",expression = "java(parseEnum(EmpStatus.bgCode(empDO.getStatus())))"),
            @Mapping(target = "type",expression = "java(parseEnum(EmpType.bgCode(empDO.getType())))"),
    })
    EmpListDTO toListDTO(EmpDO empDO);


    @Mappings({
            @Mapping(target = "gmtCreate",source = "gmtCreate.time"),
            @Mapping(target = "gmtModify",source = "gmtModify.time"),
            @Mapping(target = "status",expression = "java(parseEnum(EmpStatus.bgCode(empDO.getStatus())))"),
            @Mapping(target = "type",expression = "java(parseEnum(EmpType.bgCode(empDO.getType())))"),
    })
    EmpDTO toDTO(EmpDO empDO);



    @IterableMapping(qualifiedByName = "toListDTO")
    List<EmpListDTO> toListDTOList(List<EmpDO> empDOS);
}
