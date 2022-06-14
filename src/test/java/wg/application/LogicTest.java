package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 9:29 2022/4/27
 * @updateTime: 9:29 2022/4/27
 ************************************************************************/
@SpringBootTest
public class LogicTest {

    @Test
    public void test1() {
        int a = 1;
        int b = 2;
        int c = a + b;
        if (c == 3 || (c = 4) == 4) {
            System.out.println(c);
        }

        if ((c = 4) == 4 || (c = 3) == 3) {
            System.out.println(c);
        }
    }
}
