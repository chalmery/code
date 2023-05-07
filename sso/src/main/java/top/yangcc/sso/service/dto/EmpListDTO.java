package top.yangcc.sso.service.dto;

import lombok.Data;
import top.yangcc.sso.service.dto.base.FieldPair;

import java.io.Serializable;


@Data
public class EmpListDTO implements Serializable {

    private Long id;

    private String domain;

    private String bizCode;

    private String bizId;

    private String name;

    private String nickname;

    private Long gmtCreate;

    private Long gmtModify;

    private String avatar;

    private FieldPair status;

    private FieldPair type;
}
