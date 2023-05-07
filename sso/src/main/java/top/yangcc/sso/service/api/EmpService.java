package top.yangcc.sso.service.api;

import top.yangcc.common.result.SimpleResult;
import top.yangcc.sso.dto.EmpDTO;
import top.yangcc.sso.dto.EmpListDTO;
import top.yangcc.sso.module.SearchData;
import top.yangcc.sso.service.param.EmpListParam;

public interface EmpService {

    SimpleResult<SearchData<EmpListDTO>> list(EmpListParam param);

    SimpleResult<EmpDTO> detail(Long id);
}
