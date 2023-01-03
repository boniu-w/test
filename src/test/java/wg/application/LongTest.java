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
    public void toDatetimeTest() {
        LongUtil.toDatetime(1654499337340L);

        Long aLong = Long.valueOf(null); // java.lang.NumberFormatException
        System.out.println(aLong);
    }

    @Test
    public void parseTest() {
        String a = "1213123L";

        // long l = Long.parseLong(a);
        // System.out.println(l); // java.lang.NumberFormatException: For input string: "1213123L"

        String b = "1213123";
        long parseLong = Long.parseLong(b); // parseLong() : 转成 基础类型
        System.out.println(parseLong); // 1213123

        Long valueOf = Long.valueOf(b); // valueOf() : 转成 包装类
        System.out.println(valueOf); // 1213123

        Long getLong = Long.getLong(b);
        System.out.println(getLong); // null, 原因是: System.getProperty()
    }

}
