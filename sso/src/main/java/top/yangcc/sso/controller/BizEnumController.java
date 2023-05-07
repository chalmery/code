package top.yangcc.sso.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yangcc.common.result.SimpleResult;
import top.yangcc.sso.metadata.MetaDataService;
import top.yangcc.sso.metadata.entity.BizEnumDTO;

@RestController
@RequestMapping("/bizEnum")
@Tag(name = "业务枚举相关接口")
public class BizEnumController {

    @Resource
    @Qualifier("BizEnumService")
    private MetaDataService<BizEnumDTO> metaDataService;

    @Operation(summary = "获取单个枚举",
            description = """
                    员工类型：empType   </br>
                    员工状态：empStatus </br>
                    """)
    @GetMapping("/get")
    public SimpleResult<BizEnumDTO> get(String code) {
        SimpleResult<BizEnumDTO> result = SimpleResult.buildSuccess();
        BizEnumDTO dataValue = metaDataService.getDataValue(code);
        result.setData(dataValue);
        return result;
    }


}
