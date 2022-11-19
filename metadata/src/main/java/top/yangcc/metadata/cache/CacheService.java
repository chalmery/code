package top.yangcc.metadata.cache;

import top.yangcc.metadata.enums.LocalCacheDataType;

public interface CacheService<T>{

    /**
     * 第一次加载缓存数据
     * @return result
     */
    boolean loadCacheData();

    /**
     * 特定缓存的数据
     * @param key
     * @return
     */
    T loadData(String key);

    /**
     * 缓存的数据类型，填充关键的分区属性
     * @return
     */
    LocalCacheDataType cacheDataType();
}
