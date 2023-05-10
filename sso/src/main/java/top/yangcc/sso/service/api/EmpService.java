package top.yangcc.sso.service.api;

import top.yangcc.common.result.SimpleResult;
import top.yangcc.sso.dto.EmpDTO;
import top.yangcc.sso.dto.EmpListDTO;
import top.yangcc.sso.dto.EmpVO;
import top.yangcc.sso.dto.param.EmpListParam;
import top.yangcc.sso.module.SearchData;

public interface EmpService {

    SimpleResult<SearchData<EmpListDTO>> list(EmpListParam param);

    SimpleResult<EmpDTO> detail(Long id);

    SimpleResult<Boolean> add(EmpVO empVO);

    SimpleResult<Boolean> delete(EmpVO empVO);
}
