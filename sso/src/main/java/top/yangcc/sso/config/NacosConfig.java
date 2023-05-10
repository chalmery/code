package top.yangcc.sso.config;


import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class NacosConfig {


    @Bean
    public ConfigService ConfigService() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", "localhost:8848");
        properties.put("username","nacos");
        properties.put("password","nacos");
        return NacosFactory.createConfigService(properties);
    }
}
