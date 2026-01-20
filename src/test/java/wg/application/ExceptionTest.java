package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.exception.WgException;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:51 2022/4/27
 * @updateTime: 10:51 2022/4/27
 ************************************************************************/
@SpringBootTest
public class ExceptionTest {

    @Test
    public void test1() {
        // throw new RuntimeException("强制测试事务回滚");
        // int a = 1 / 0;
        if (1 == 1) {
            throw new WgException("123");
        }
        System.out.println(123);
    }
}
