package top.yangcc.sso.dao.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("sso_dept")
public class DeptDO implements Serializable {


    @TableId(type = IdType.AUTO)
    private Long id;

    private Date gmtCreate;

    private Date gmtModify;

    private Boolean archive;

    private String deptCode;

    private String deptName;

    private String deptEnName;

    private String parentCode;

}