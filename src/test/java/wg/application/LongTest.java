package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.LongUtil;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 15:20 2022/6/6
 * @updateTime: 15:20 2022/6/6
 ************************************************************************/
@SpringBootTest
public class LongTest {

    @Test
    public void toDatetimeTest(){
        LongUtil.toDatetime(1654499337340L);
    }
}
