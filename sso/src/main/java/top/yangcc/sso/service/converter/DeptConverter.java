package top.yangcc.sso.service.converter;


import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import top.yangcc.sso.dao.dataobject.DeptDO;
import top.yangcc.sso.dto.DeptDTO;
import top.yangcc.sso.dto.DeptVO;
import top.yangcc.sso.dto.base.FieldPair;
import top.yangcc.sso.enums.EmpStatus;
import top.yangcc.sso.enums.EmpType;

import java.util.Date;
import java.util.List;

@Mapper(imports = {FieldPair.class, EmpStatus.class, EmpType.class, Date.class})
public interface DeptConverter extends BaseConverter{
    DeptConverter INSTANCE = Mappers.getMapper(DeptConverter.class);


    @Named("toDTO")
    @Mappings({
            @Mapping(target = "gmtCreate", source = "gmtCreate.time"),
            @Mapping(target = "gmtModify", source = "gmtModify.time"),
    })
    DeptDTO toDTO(DeptDO deptDO);


    @IterableMapping(qualifiedByName = "toDTO")
    List<DeptDTO> toDTOList(List<DeptDO> empDOS);



    @Mappings({
            @Mapping(target = "archive", constant = "false"),
            @Mapping(target = "gmtCreate", ignore = true),
            @Mapping(target = "gmtModify", expression = "java(new Date())"),
    })
    DeptDO toDO(DeptVO deptVO);
}
