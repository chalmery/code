package top.yangcc.sso.service.api;

import top.yangcc.common.result.SimpleResult;
import top.yangcc.sso.dto.DeptDTO;
import top.yangcc.sso.dto.DeptVO;
import top.yangcc.sso.dto.param.DeptListParam;
import top.yangcc.sso.module.SearchData;

public interface DeptService {

    SimpleResult<SearchData<DeptDTO>> list(DeptListParam param);

    SimpleResult<DeptDTO> detail(Long id);

    SimpleResult<Boolean> add(DeptVO deptVO);

    SimpleResult<Boolean> delete(DeptVO deptVO);
}
