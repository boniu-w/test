package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.ComputerUtil;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 14:17 2022/8/9
 * @updateTime: 14:17 2022/8/9
 ************************************************************************/
@SpringBootTest
public class ComputerTest {

    @Test
    public void test1(){
        ComputerUtil.getHardware();
    }
}
