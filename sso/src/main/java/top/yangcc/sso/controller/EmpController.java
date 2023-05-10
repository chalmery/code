package top.yangcc.sso.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.yangcc.common.result.SimpleResult;
import top.yangcc.sso.dto.EmpDTO;
import top.yangcc.sso.dto.EmpListDTO;
import top.yangcc.sso.dto.EmpVO;
import top.yangcc.sso.dto.param.EmpListParam;
import top.yangcc.sso.module.SearchData;
import top.yangcc.sso.service.api.EmpService;

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



    @Operation(summary = "新增/编辑员工信息")
    @PostMapping("/add")
    public SimpleResult<Boolean> add(@RequestBody @Validated EmpVO empVO) {
        if (Objects.isNull(empVO)) {
            return SimpleResult.buildSuccess();
        }
        return empService.add(empVO);
    }

    @Operation(summary = "删除员工")
    @PostMapping("/delete")
    public SimpleResult<Boolean> delete(@RequestBody EmpVO empVO) {
        if (Objects.isNull(empVO) || Objects.isNull(empVO.getId())) {
            return SimpleResult.buildSuccess();
        }
        return empService.delete(empVO);
    }

}
