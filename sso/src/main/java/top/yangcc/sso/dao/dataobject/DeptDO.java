package top.yangcc.sso.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("sso_dept")
public class DeptDO implements Serializable {

    private Long id;

    private Date gmtCreate;

    private Date gmtModify;

    private Boolean archive;

    private Integer deptCode;

    private String depName;

    private String deptEnName;

    private Integer parentCode;

}