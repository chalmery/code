package top.yangcc.core.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.yangcc.core.ElasticSearchFacadeImpl;
import top.yangcc.core.EsClientPoolConfig;
import top.yangcc.facade.ElasticSearchFacade;

@Configuration
@EnableConfigurationProperties({BizSearchProperties.class})
public class ElasticAutoConfiguration {


    @Bean
    public EsClientPoolConfig clientPoolConfig(BizSearchProperties searchProperties) throws Exception {
        EsClientPoolConfig poolConfig = new EsClientPoolConfig();
        poolConfig.setSearchProperties(searchProperties);
        poolConfig.init();
        return poolConfig;
    }

    @Bean
    public ElasticSearchFacade searchFacade(EsClientPoolConfig clientPoolConfig){
        ElasticSearchFacadeImpl searchFacade = new ElasticSearchFacadeImpl();
        searchFacade.setClientPool(clientPoolConfig);
        return searchFacade;
    }

}
