package top.yangcc.sso.nacos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONValidator;
import com.alibaba.nacos.api.config.ConfigService;
import top.yangcc.sso.utils.SpringApplicationContextUtil;

import java.io.ByteArrayInputStream;
import java.util.Properties;

public class ConfigClient<T> {

    private final ConfigService configService;

    public ConfigClient() {
        configService = SpringApplicationContextUtil.getBean(ConfigService.class);
    }

    public T getConfig(Class<T> tClass,String dataId,String groupId){
        try {
            String config = configService.getConfig(dataId, groupId, 5000);
            boolean isJson = false;
            try {
                isJson =  JSONValidator.from(config).validate();
            }catch (Exception ignored){
            }
            if (isJson){
                return JSON.parseObject(config,tClass);
            }else {
                Properties properties = new Properties();
                properties.load(new ByteArrayInputStream(config.getBytes()));
                return JSON.parseObject(JSON.toJSONString(properties),tClass);
            }
        }catch (Exception e){
            return null;
        }
    }


}
