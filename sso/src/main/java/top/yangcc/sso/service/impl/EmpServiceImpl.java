package top.yangcc.sso.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.yangcc.common.result.SimpleResult;
import top.yangcc.sso.dao.api.EmpMapper;
import top.yangcc.sso.dao.dataobject.EmpDO;
import top.yangcc.sso.dto.EmpDTO;
import top.yangcc.sso.dto.EmpListDTO;
import top.yangcc.sso.dto.EmpVO;
import top.yangcc.sso.dto.param.EmpListParam;
import top.yangcc.sso.enums.ArchiveEnum;
import top.yangcc.sso.module.PageInfo;
import top.yangcc.sso.module.SearchData;
import top.yangcc.sso.service.api.EmpService;
import top.yangcc.sso.service.converter.EmpConverter;

import java.util.Date;
import java.util.Objects;


@Service
@Slf4j
public class EmpServiceImpl implements EmpService {

    @Resource
    private EmpMapper empMapper;

    private final EmpConverter empConverter = EmpConverter.INSTANCE;

    @Override
    public SimpleResult<SearchData<EmpListDTO>> list(EmpListParam param) {

        SimpleResult<SearchData<EmpListDTO>> result = SimpleResult.buildSuccess();

        LambdaQueryWrapper<EmpDO> wrapper = new LambdaQueryWrapper<>(EmpDO.class);

        wrapper.eq(EmpDO::getArchive, ArchiveEnum.NO.getCode());
        wrapper.orderByDesc(EmpDO::getGmtCreate);

        if (Objects.nonNull(param.getId())){
            wrapper.eq(EmpDO::getId,param.getId());
        }
        if (StringUtils.isNoneBlank(param.getName())){
            wrapper.like(EmpDO::getName,param.getName());
        }
        if (StringUtils.isNoneBlank(param.getNickname())){
            wrapper.like(EmpDO::getNickname,param.getName());
        }


        Page<EmpDO> page = new Page<>(param.getPage(), param.getLimit());
        Page<EmpDO> resultData = empMapper.selectPage(page, wrapper);
        Long count = empMapper.selectCount(wrapper);

        SearchData<EmpListDTO> searchData = new SearchData<>();
        searchData.setPageInfo(new PageInfo(param.getPage(), param.getLimit()));
        searchData.setCount(count);
        searchData.setData(empConverter.toListDTOList(resultData.getRecords()));
        result.setData(searchData);
        return result;
    }

    @Override
    public SimpleResult<EmpDTO> detail(Long id) {
        SimpleResult<EmpDTO> result = SimpleResult.buildSuccess();
        EmpDO empDO = empMapper.selectById(id);
        result.setData(empConverter.toDTO(empDO));
        return result;
    }

    @Override
    public SimpleResult<Boolean> add(EmpVO empVO) {
        EmpDO empDO = empConverter.toDO(empVO);
        log.info("[EmpServiceImpl][add][start][{}]",empDO);
        int count;
        if (Objects.nonNull(empVO.getId())){
            count = empMapper.updateById(empDO);
        }else {
            empDO.setGmtCreate(new Date());
            count = empMapper.insert(empDO);
        }
        if (count>0){
            return SimpleResult.buildSuccess(Boolean.TRUE);
        }
        return SimpleResult.buildSuccess(Boolean.FALSE);
    }

    @Override
    public SimpleResult<Boolean> delete(EmpVO empVO) {
        SimpleResult<Boolean> result = SimpleResult.buildSuccess(Boolean.TRUE);
        Long id = empVO.getId();
        EmpDO empDO = new EmpDO();
        empDO.setId(id);
        empMapper.updateById(empDO);
        return result;
    }


}
