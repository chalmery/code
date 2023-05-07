package top.yangcc.sso.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yangcc.common.result.SimpleResult;
import top.yangcc.sso.dto.EmpDTO;
import top.yangcc.sso.dto.EmpListDTO;
import top.yangcc.sso.module.SearchData;
import top.yangcc.sso.service.api.EmpService;
import top.yangcc.sso.service.param.EmpListParam;

import java.util.Objects;

@RestController
@RequestMapping("/emp")
@Tag(name = "员工域相关接口")
public class EmpController {

    @Resource
    private EmpService empService;


    @Operation(summary = "员工信息列表")
    @GetMapping("/list")
    public SimpleResult<SearchData<EmpListDTO>> list(EmpListParam param) {
        return empService.list(param);
    }


    @Operation(summary = "员工信息详情")
    @GetMapping("/detail")
    public SimpleResult<EmpDTO> detail(Long id) {
        if (Objects.isNull(id)) {
            return SimpleResult.buildSuccess();
        }
        return empService.detail(id);
    }



    @Operation(summary = "员工信息详情")
    @GetMapping("/add")
    public SimpleResult<EmpDTO> add(Long id) {
        if (Objects.isNull(id)) {
            return SimpleResult.buildSuccess();
        }
        return empService.detail(id);
    }

}
