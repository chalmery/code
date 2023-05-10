package top.yangcc.sso.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.yangcc.common.result.SimpleResult;
import top.yangcc.sso.sal.api.SsoService;

import java.util.Objects;

@RestController
@RequestMapping("/oss")
@Tag(name = "对象存储相关接口")
@Slf4j
public class OssController {

    @Resource
    private SsoService ssoService;



    @Operation(summary = "文件上传")
    @PostMapping("/upload")
    public SimpleResult<String> get(@RequestParam("file") MultipartFile file) {
        try{
            SimpleResult<String> result  = SimpleResult.buildSuccess();
            if (Objects.isNull(file)){
                return result;
            }
            String url = ssoService.upload(file.getBytes());
            result.setData(url);
            return result;
        }catch (Exception e){
            log.info("[OssController][get][exception][{}]",e.getMessage());
            return SimpleResult.buildError(e.getMessage());
        }
    }
}


