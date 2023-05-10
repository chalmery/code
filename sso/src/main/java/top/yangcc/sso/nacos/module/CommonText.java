package top.yangcc.sso.nacos.module;

import lombok.Data;

import java.io.Serializable;


@Data
public class CommonText implements Serializable {

    public static final String COMMON_TEXT = "common_text";

    public static final String GROUP_ID = "SSO-GROUP";

    /**
     * 七牛云ak
     */
    private String ossAk;

    /**
     * 七牛云sk
     */
    private String ossSk;

    /**
     * ossBucket
     */
    private String ossBucket;

    /**
     * base url
     */
    private String ossUrl;

}
