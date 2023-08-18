package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.exception.TheException;

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
        if (1 == 1) {
            throw new TheException("123");
        }
        System.out.println(123);
    }
}
