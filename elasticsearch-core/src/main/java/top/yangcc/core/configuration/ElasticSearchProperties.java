package top.yangcc.core.configuration;

import lombok.Data;

@Data
public class ElasticSearchProperties {

    private String userName;

    private String password;

    private Integer port;

    private String host;
}
