package top.yangcc.sso.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("biz_enum")
public class BizEnumDO implements Serializable {

    private Long id;

    private Date gmtCreate;

    private Date gmtModify;

    private Boolean archive;

    private String name;

    private String code;

    private String value;

}
