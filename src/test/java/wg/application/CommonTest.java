package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.CommonUtil;

@SpringBootTest
public class CommonTest {

    @Test
    public void boo() {
        Double a = null;
        Double b = 0d;
        boolean b1 = CommonUtil.allTrue(() -> a != null, () -> b.compareTo(0d) != 0);
        System.out.println("b1 = " + b1);
    }
}
