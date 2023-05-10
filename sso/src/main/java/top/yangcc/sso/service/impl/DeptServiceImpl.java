package top.yangcc.sso.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.yangcc.common.result.SimpleResult;
import top.yangcc.sso.dao.api.DeptMapper;
import top.yangcc.sso.dao.dataobject.DeptDO;
import top.yangcc.sso.dto.DeptDTO;
import top.yangcc.sso.dto.DeptVO;
import top.yangcc.sso.dto.param.DeptListParam;
import top.yangcc.sso.enums.ArchiveEnum;
import top.yangcc.sso.module.PageInfo;
import top.yangcc.sso.module.SearchData;
import top.yangcc.sso.service.api.DeptService;
import top.yangcc.sso.service.converter.DeptConverter;

import java.util.Date;
import java.util.Objects;


@Service
@Slf4j
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    private final DeptConverter deptConverter = DeptConverter.INSTANCE;

    @Override
    public SimpleResult<SearchData<DeptDTO>> list(DeptListParam param) {

        SimpleResult<SearchData<DeptDTO>> result = SimpleResult.buildSuccess();

        LambdaQueryWrapper<DeptDO> wrapper = new LambdaQueryWrapper<>(DeptDO.class);

        wrapper.eq(DeptDO::getArchive, ArchiveEnum.NO.getCode());
        wrapper.orderByDesc(DeptDO::getGmtCreate);

        if (Objects.nonNull(param.getId())){
            wrapper.eq(DeptDO::getId,param.getId());
        }
        if (StringUtils.isNoneBlank(param.getDeptCode())){
            wrapper.like(DeptDO::getDeptCode,param.getDeptCode());
        }
        if (StringUtils.isNoneBlank(param.getDeptName())){
            wrapper.like(DeptDO::getDeptName,param.getDeptName());
        }
        if (StringUtils.isNoneBlank(param.getDeptEnName())){
            wrapper.like(DeptDO::getDeptEnName,param.getDeptEnName());
        }


        Page<DeptDO> page = new Page<>(param.getPage(), param.getLimit());
        Page<DeptDO> resultData = deptMapper.selectPage(page, wrapper);
        Long count = deptMapper.selectCount(wrapper);

        SearchData<DeptDTO> searchData = new SearchData<>();
        searchData.setPageInfo(new PageInfo(param.getPage(), param.getLimit()));
        searchData.setCount(count);
        searchData.setData(deptConverter.toDTOList(resultData.getRecords()));
        result.setData(searchData);
        return result;
    }

    @Override
    public SimpleResult<DeptDTO> detail(Long id) {
        SimpleResult<DeptDTO> result = SimpleResult.buildSuccess();
        DeptDO deptDO = deptMapper.selectById(id);
        result.setData(deptConverter.toDTO(deptDO));
        return result;
    }

    @Override
    public SimpleResult<Boolean> add(DeptVO deptVO) {
        DeptDO deptDO = deptConverter.toDO(deptVO);
        log.info("[DeptServiceImpl][add][start][{}]",deptDO);
        int count;
        if (Objects.nonNull(deptVO.getId())){
            count = deptMapper.updateById(deptDO);
        }else {
            deptDO.setGmtCreate(new Date());
            count = deptMapper.insert(deptDO);
        }
        if (count>0){
            return SimpleResult.buildSuccess(Boolean.TRUE);
        }
        return SimpleResult.buildSuccess(Boolean.FALSE);
    }

    @Override
    public SimpleResult<Boolean> delete(DeptVO deptVO) {
        SimpleResult<Boolean> result = SimpleResult.buildSuccess(Boolean.TRUE);
        Long id = deptVO.getId();
        DeptDO deptDO = new DeptDO();
        deptDO.setId(id);
        deptMapper.updateById(deptDO);
        return result;
    }


}
