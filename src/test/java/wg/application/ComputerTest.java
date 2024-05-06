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

    /**
     * @author: wg
     * @description: [oshi.hardware.NetworkIF@5427c60c, oshi.hardware.NetworkIF@15bfd87, oshi.hardware.NetworkIF@543e710e,
     * oshi.hardware.NetworkIF@57f23557, oshi.hardware.NetworkIF@3d0f8e03, oshi.hardware.NetworkIF@6366ebe0, oshi.hardware.NetworkIF@44f75083,
     * oshi.hardware.NetworkIF@2698dc7, oshi.hardware.NetworkIF@43d7741f, oshi.hardware.NetworkIF@17baae6e]
     * @params:
     * @return:
     * @createTime: 15:55  2024/4/30
     * @updateTime: 15:55  2024/4/30
     */
    @Test
    public void test1(){
        ComputerUtil.getHardware();
    }
}
