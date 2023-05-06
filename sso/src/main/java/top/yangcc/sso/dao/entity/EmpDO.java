package top.yangcc.sso.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("sso_emp")
public class EmpDO implements Serializable {

    private Long id;

    private String domain;

    private String bizCode;

    private String bizId;

    private Boolean archive;

    private String name;

    private String nickname;

    private String password;

    private Date gmtCreate;

    private Date gmtModify;

    private String avatar;

}
