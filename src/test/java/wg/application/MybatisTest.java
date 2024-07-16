package wg.application;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.entity.User;
import wg.application.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 11:02 2022/8/25
 * @updateTime: 11:02 2022/8/25
 ************************************************************************/
@SpringBootTest
public class MybatisTest {

    @Resource
    SqlSessionFactory sqlSessionFactory;

    @Resource
    SqlSessionTemplate sqlSessionTemplate;

    // @Resource
    // UserMapper userMapper;

    @Test
    public void updateTest() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        ArrayList<User> userList = new ArrayList<>();

    }

    /**
     * @param
     * @return
     * @author wg
     * @description sqlSession 生命周期
     * @createTime 13:55  2024/7/10
     * @updateTime 13:55  2024/7/10
     */
    public void test() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> list = sqlSession.selectList("select * from user");
        // sqlSession.commit(); 查询不需要 commit , add, update, remove 需要
        sqlSession.close();
    }
}
