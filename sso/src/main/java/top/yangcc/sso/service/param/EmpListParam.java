package top.yangcc.sso.service.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yangcc.common.result.module.PageRequest;


@EqualsAndHashCode(callSuper = true)
@Data
public class EmpListParam extends PageRequest {

    private Long id;

    private String domain;

    private String bizCode;

    private String bizId;

    private String name;

    private String nickname;

    private Long gmtCreate;

}
