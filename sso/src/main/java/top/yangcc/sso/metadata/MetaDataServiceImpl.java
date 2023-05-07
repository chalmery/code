package top.yangcc.sso.metadata;

import top.yangcc.sso.metadata.entity.MetaDataDTO;
import top.yangcc.sso.metadata.entity.MeteDataTypeEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MetaDataServiceImpl<T extends MetaDataDTO> implements MetaDataService<T> {

    public Map<MeteDataTypeEnum, List<T>> CACHE_MAP = new HashMap<>();


    /**
     * 初次启动初始化
     */
    abstract void init();


}
