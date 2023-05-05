package top.yangcc.sso;

import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yangcc.sso.dao.api.UserMapper;
import top.yangcc.sso.dao.entity.UserDO;

import java.util.List;

@SpringBootTest
class SsoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        List<UserDO> userList = userMapper.selectList(null);
        System.out.println(JSONArray.toJSONString(userList));
    }

}
