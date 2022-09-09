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

    /************************************************************************
     * @author: wg
     * @description: 断路符
     * @params:
     * @return:
     * @createTime: 9:24  2022/8/30
     * @updateTime: 9:24  2022/8/30
     ************************************************************************/
    @Test
    public void testCircuitBreaker() {
        int a = 1;
        int b = 2;
        int c = a + b;
        if (c == 3 || (c = 4) == 4) {
            System.out.println(c); // 3
        }

        if ((c = 4) == 4 || (c = 3) == 3) {
            System.out.println(c); // 4
        }
    }

    @Test
    public void testBooleanAndInteger() {
        boolean a = true;
        int b = 1;
        // System.out.println(a==b); // Operator '==' cannot be applied to 'boolean', 'int'
    }
}
