package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.SpringContextUtils;

/************************************************************************
 * author: wg
 * description: JedisTest 
 * createTime: 10:36 2022/12/6
 * updateTime: 10:36 2022/12/6
 ************************************************************************/
@SpringBootTest
public class JedisTest {
    // @Test
    // public void testRedisConfig() {
    //     String host = redisConfig.getHost();
    //     System.out.println(host);
    // }

    /************************************************************************
     * @author: wg
     * @description:
     * 结论: 可能是版本原因, test 时 不是 全程序启动, 因此获取不到 bean, 用controller 测试, 是 可以的
     * @params:
     * @return:
     * @createTime: 11:13  2022/12/6
     * @updateTime: 11:13  2022/12/6
     ************************************************************************/
    @Test
    public void testJedis() {
        // Object redisConfig = SpringContextUtils.getBean("redisConfig"); // null pointer exception
        // System.out.println(redisConfig);
        // JedisUtilTest.testSetElements();
        // JedisUtilTest.testSetEnsemble();
    }
}
