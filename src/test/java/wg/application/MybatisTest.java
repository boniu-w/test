package wg.application;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.entity.User;
import wg.application.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.ArrayList;

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

    // @Resource
    // UserMapper userMapper;

    @Test
    public void updateTest(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        ArrayList<User> userList = new ArrayList<>();

    }
}
