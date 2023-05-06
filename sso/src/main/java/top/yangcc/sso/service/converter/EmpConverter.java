package top.yangcc.sso.service.converter;


import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import top.yangcc.sso.dao.entity.EmpDO;
import top.yangcc.sso.service.dto.EmpListDTO;

import java.util.List;

@Mapper
public interface EmpConverter {
    EmpConverter INSTANCE = Mappers.getMapper(EmpConverter.class);


    @Named("toListDTO")
    @Mappings({
            @Mapping(target = "gmtCreate",source = "gmtCreate.time"),
            @Mapping(target = "gmtModify",source = "gmtModify.time"),
    })
    EmpListDTO toListDTO(EmpDO empDO);



    @IterableMapping(qualifiedByName = "toListDTO")
    List<EmpListDTO> toListDTOList(List<EmpDO> empDOS);
}
