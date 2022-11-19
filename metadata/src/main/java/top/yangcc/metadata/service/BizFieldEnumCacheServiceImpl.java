package top.yangcc.metadata.service;

import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yangcc.common.result.ListResult;
import top.yangcc.metadata.dao.bizfield.BizFieldEnumDao;
import top.yangcc.metadata.dao.bizfield.PageInfo;
import top.yangcc.metadata.dto.CacheData;
import top.yangcc.metadata.dto.fieldenum.FieldEnumCache;
import top.yangcc.metadata.enums.LocalCacheDataType;

@Service
@Slf4j
public class BizFieldEnumCacheServiceImpl extends AbstractCacheServiceImpl<CacheData<FieldEnumCache>> {

    @Autowired
    private BizFieldEnumDao bizFieldEnumDao;

    @Override
    public LocalCacheDataType cacheDataType() {
        return LocalCacheDataType.BIZ_FIELD;
    }

    /**
     * 业务枚举总条数
     * @return count
     */
    @Override
    protected Long totalCount() {
        return bizFieldEnumDao.count();
    }

    /**
     * 加载枚举
     * @param dto  PageInfo
     * @param cacheInstance cacheInstance
     * @return ListResult
     */
    @Override
    protected ListResult<CacheData<FieldEnumCache>> loadListData(PageInfo dto, Cache<String, Object> cacheInstance) {

        return null;
    }
}
