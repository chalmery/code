package top.yangcc.sso.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import top.yangcc.sso.SsoApplicationTests;
import top.yangcc.sso.dao.api.UserMapper;
import top.yangcc.sso.dao.entity.UserDO;

import java.util.List;

public class UserTest extends SsoApplicationTests {


    @Autowired
    private UserMapper userMapper;


    @Test
    public void testList(){
        List<UserDO> userList = userMapper.selectList(null);
        Assert.isTrue(userList!=null && userList.size()>0,"false");
    }


}
