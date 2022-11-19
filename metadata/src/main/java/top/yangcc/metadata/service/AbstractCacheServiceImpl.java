package top.yangcc.metadata.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import top.yangcc.common.result.ListResult;
import top.yangcc.metadata.cache.CacheService;
import top.yangcc.metadata.dao.bizfield.PageInfo;
import top.yangcc.metadata.dto.CacheData;
import top.yangcc.metadata.enums.LocalCacheDataType;

import java.util.Objects;

@Slf4j
public abstract class AbstractCacheServiceImpl<T extends CacheData> implements CacheService<T> {

    public static final int PAGE_SIZE = 100;

    /**
     * 第一次加载缓存
     *
     * @return 成功/失败
     */
    @Override
    public boolean loadCacheData() {
        log.info("[cacheType][{}],缓存加载开始", cacheDataType().getName());
        boolean result = false;
        Long count = totalCount();
        if (Objects.isNull(count) || count == 0) {
            result = false;
        } else {
            Cache<Object, Object> cacheInstance = CacheBuilder.newBuilder()
                    .maximumSize(count)
                    .build();


        }
        return false;
    }

    @Override
    public T loadData(String key) {
        return null;
    }

    public abstract LocalCacheDataType cacheDataType();

    /**
     * 缓存总条数
     */
    protected abstract Long totalCount();


    protected abstract ListResult<T> loadListData(PageInfo dto, Cache<String, Object> cacheInstance);


    /**
     * 缓存分页
     */
    private boolean cachePartitionData(Integer pageNum, Cache<String, Object> cacheInstance) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(pageNum + 1);
        pageInfo.setLimit(PAGE_SIZE);
        ListResult<T> listResult = loadListData(pageInfo, cacheInstance);
        if (Objects.nonNull(listResult) && CollectionUtils.isNotEmpty(listResult.getData())) {
            listResult.getData().stream().filter(Objects::nonNull).forEach(data -> {
                if (Objects.nonNull(data.getData())) {
                    cacheInstance.put(data.getKey(), data);
                } else {
                    log.error("[cacheKey][{}] is null", data.getKey());
                }
            });
        }
        return true;
    }
}
