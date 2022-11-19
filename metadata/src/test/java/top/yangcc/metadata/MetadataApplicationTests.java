package top.yangcc.metadata;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootTest
class MetadataApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
        log.info("启动");
        Long count = jdbcTemplate.queryForObject("select count(*) from biz_enum", Long.class);
        log.info("条数{}",count);
    }

}
