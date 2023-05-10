package top.yangcc.sso.sal.impl;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yangcc.sso.nacos.ConfigClient;
import top.yangcc.sso.nacos.module.CommonText;
import top.yangcc.sso.sal.api.SsoService;
import top.yangcc.sso.utils.DateUtils;


@Service
@Slf4j
public class QiNiuSsoServiceImpl implements SsoService {

    @Override
    public String upload(byte[] bytes) throws Exception {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        CommonText config = new ConfigClient<CommonText>().getConfig(CommonText.class, CommonText.COMMON_TEXT, CommonText.GROUP_ID);

        //...生成上传凭证，然后准备上传
        String accessKey = config.getOssAk();
        String secretKey = config.getOssSk();
        String bucket = config.getOssBucket();
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "project/sso/" + DateUtils.format();
        log.info("[QiNiuSsoServiceImpl][upload][key][{}]", key);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Response response = uploadManager.put(bytes, key, upToken);
        //解析上传成功的结果
        DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
        return config.getOssUrl() + putRet.key;
    }
}
