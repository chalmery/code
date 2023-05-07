package top.yangcc.sso.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.yangcc.common.result.SimpleResult;
import top.yangcc.common.result.module.Page;
import top.yangcc.common.result.module.SearchData;
import top.yangcc.sso.dao.api.EmpMapper;
import top.yangcc.sso.dao.dataobject.EmpDO;
import top.yangcc.sso.service.api.EmpService;
import top.yangcc.sso.service.converter.EmpConverter;
import top.yangcc.sso.service.dto.EmpListDTO;
import top.yangcc.sso.service.param.EmpListParam;

import java.util.List;


@Service
public class EmpServiceImpl implements EmpService {

    @Resource
    private EmpMapper empMapper;

    private final EmpConverter empConverter = EmpConverter.INSTANCE;

    @Override
    public SimpleResult<SearchData<EmpListDTO>> list(EmpListParam param) {

        SimpleResult<SearchData<EmpListDTO>> result = SimpleResult.buildSuccess();

        List<EmpDO> userList = empMapper.selectList(null);

        Long count = empMapper.selectCount(null);

        SearchData<EmpListDTO> searchData = new SearchData<>();
        searchData.setPage(new Page(param.getPage(), param.getLimit()));
        searchData.setCount(count);
        searchData.setData(empConverter.toListDTOList(userList));
        result.setData(searchData);
        return result;
    }
}
