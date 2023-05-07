package top.yangcc.sso.metadata;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.yangcc.sso.dao.api.BizEnumMapper;
import top.yangcc.sso.dao.dataobject.BizEnumDO;
import top.yangcc.sso.metadata.converter.MetaConverter;
import top.yangcc.sso.metadata.entity.BizEnumDTO;
import top.yangcc.sso.metadata.entity.MeteDataTypeEnum;

import java.util.List;

@Service("BizEnumService")
@Slf4j
public class BizEnumServiceImpl extends MetaDataServiceImpl<BizEnumDTO> {
    @Resource
    private BizEnumMapper bizEnumMapper;


    private final MetaConverter converter = MetaConverter.INSTANCE;

    @PostConstruct
    @Override
    void init() {
        List<BizEnumDO> bizEnumDOS = bizEnumMapper.selectList(null);
        if (CollectionUtils.isEmpty(bizEnumDOS)){
            return;
        }
        log.info("[enum][cache][start][{}]",bizEnumDOS.size());
        CACHE_MAP.put(MeteDataTypeEnum.BIZ_ENUM,converter.toDTOList(bizEnumDOS));
        log.info("[enum][cache][end]");
    }

    @Override
    public BizEnumDTO getDataValue(String code) {
        if (StringUtils.isEmpty(code)){
            return null;
        }
        List<BizEnumDTO> bizEnumDTOS = CACHE_MAP.get(MeteDataTypeEnum.BIZ_ENUM);
        if (CollectionUtils.isEmpty(bizEnumDTOS)){
            return null;
        }
        return bizEnumDTOS.stream()
                .filter(bizEnumDTO -> bizEnumDTO.getCode().equals(code))
                .findAny().orElse(null);
    }
}
