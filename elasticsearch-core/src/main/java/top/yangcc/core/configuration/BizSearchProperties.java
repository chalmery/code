package top.yangcc.core.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "yangcc.es")
public class BizSearchProperties {

    private String bizTypeId;

    private String indexType;

    private String searchAlias;

    @Setter
    @Getter
    private ElasticSearchProperties search;
}
