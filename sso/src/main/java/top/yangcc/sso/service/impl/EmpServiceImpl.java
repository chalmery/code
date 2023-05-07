package top.yangcc.sso.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.yangcc.common.result.SimpleResult;
import top.yangcc.sso.dao.api.EmpMapper;
import top.yangcc.sso.dao.dataobject.EmpDO;
import top.yangcc.sso.dto.EmpDTO;
import top.yangcc.sso.dto.EmpListDTO;
import top.yangcc.sso.dto.EmpVO;
import top.yangcc.sso.enums.ArchiveEnum;
import top.yangcc.sso.module.Page;
import top.yangcc.sso.module.SearchData;
import top.yangcc.sso.service.api.EmpService;
import top.yangcc.sso.service.converter.EmpConverter;
import top.yangcc.sso.service.param.EmpListParam;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
public class EmpServiceImpl implements EmpService {

    @Resource
    private EmpMapper empMapper;

    private final EmpConverter empConverter = EmpConverter.INSTANCE;

    @Override
    public SimpleResult<SearchData<EmpListDTO>> list(EmpListParam param) {

        SimpleResult<SearchData<EmpListDTO>> result = SimpleResult.buildSuccess();

        LambdaQueryWrapper<EmpDO> wrapper = new LambdaQueryWrapper<>(EmpDO.class);

        wrapper.eq(EmpDO::getAvatar, ArchiveEnum.NO.getCode());

        if (Objects.nonNull(param.getId())){
            wrapper.eq(EmpDO::getId,param.getId());
        }
        if (StringUtils.isNoneBlank(param.getName())){
            wrapper.like(EmpDO::getName,param.getName());
        }
        if (StringUtils.isNoneBlank(param.getNickname())){
            wrapper.like(EmpDO::getNickname,param.getName());
        }


        List<EmpDO> userList = empMapper.selectList(wrapper);

        Long count = empMapper.selectCount(wrapper);

        SearchData<EmpListDTO> searchData = new SearchData<>();
        searchData.setPage(new Page(param.getPage(), param.getLimit()));
        searchData.setCount(count);
        searchData.setData(empConverter.toListDTOList(userList));
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


}
