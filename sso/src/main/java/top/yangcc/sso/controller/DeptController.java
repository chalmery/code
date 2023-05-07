package top.yangcc.sso.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.yangcc.common.result.SimpleResult;
import top.yangcc.sso.dto.DeptDTO;
import top.yangcc.sso.dto.DeptVO;
import top.yangcc.sso.dto.param.DeptListParam;
import top.yangcc.sso.module.SearchData;
import top.yangcc.sso.service.api.DeptService;

import java.util.Objects;

@RestController
@RequestMapping("/emp")
@Tag(name = "部门域相关接口")
public class DeptController {

    @Resource
    private DeptService deptService;


    @Operation(summary = "部门信息列表")
    @GetMapping("/list")
    public SimpleResult<SearchData<DeptDTO>> list(DeptListParam param) {
        return deptService.list(param);
    }


    @Operation(summary = "部门信息详情")
    @GetMapping("/detail")
    public SimpleResult<DeptDTO> detail(Long id) {
        if (Objects.isNull(id)) {
            return SimpleResult.buildSuccess();
        }
        return deptService.detail(id);
    }



    @Operation(summary = "新增/编辑部门信息")
    @PostMapping("/add")
    public SimpleResult<Boolean> add(@RequestBody @Validated DeptVO deptVO) {
        if (Objects.isNull(deptVO)) {
            return SimpleResult.buildSuccess();
        }
        return deptService.add(deptVO);
    }

}
