package wg.application;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/*****************************************
 * description:
 * date: 13:43 2021/7/26
 * auth: wg
 *****************************************/
@SpringBootTest
public class StringTest {

    /*****************************************************
     * @params:
     * @description: (String)、toString、String.valueOf 的区别
     * @author: wg
     * @date: 2021/8/12 11:46
     *****************************************************/
    @Test
    public void test1() {

        Object s = null;

        System.out.println(String.valueOf(s) == null); // false
        System.out.println(String.valueOf(s).equals("null")); // true

        if (StringUtils.isBlank(String.valueOf(s))) {
            System.out.println("s is blank");
        }

        String s1 = (String) s; // 不报异常
        s.toString(); // 报异常 空指针

        Object a = new Integer(1);
        String as = (String) a; // 报异常 ClassCastException  .Integer cannot be cast to java.lang.String
    }
}
