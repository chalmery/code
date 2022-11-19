package top.yangcc.metadata.dao.bizfield;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class BizFieldEnumDaoImpl implements BizFieldEnumDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long count() {
        Long count = jdbcTemplate.queryForObject("select count(*) from biz_enum", Long.class);
        log.info("[bizField][count][{}]",count);
        return count;
    }
}
