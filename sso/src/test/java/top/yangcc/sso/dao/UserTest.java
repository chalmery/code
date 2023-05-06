package top.yangcc.sso.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import top.yangcc.sso.SsoApplicationTests;
import top.yangcc.sso.dao.api.EmpMapper;
import top.yangcc.sso.dao.entity.EmpDO;

import java.util.List;

public class UserTest extends SsoApplicationTests {


    @Autowired
    private EmpMapper empMapper;


    @Test
    public void testList(){
        List<EmpDO> userList = empMapper.selectList(null);
        Assert.isTrue(userList!=null && userList.size()>0,"false");
    }


}
