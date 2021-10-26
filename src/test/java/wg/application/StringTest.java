package wg.application;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    /************************************************************************
     * @description: contains
     * @author: wg
     * @date: 17:00  2021/9/6
     ************************************************************************/
    @Test
    public void testContains() {
        String s1 = "混合动力";

        if (s1.contains("混合")) {
            System.out.println("****8  " + s1.indexOf("混合"));
        }

        if (s1.contains("混里")) {
            System.out.println("----- " + s1.indexOf("混里"));
        }

        String s2 = "2021-08-18 风险评价 10";
        String s3 = " 2021-08-18 风险评价 10";

        // System.out.println(s2.contains(s3.trim()));
        // System.out.println(s3.contains(s2.trim()));

        List<String> list = Collections.singletonList(s2);

        list = list.stream().filter(s -> s.contains(s3.trim())).collect(Collectors.toList());

        System.out.println(list);
    }

    @Test
    public void test3() {
        String[] s = new String[0];
        System.out.println(Arrays.toString(s));

        Locale locale = LocaleContextHolder.getLocale();
        System.out.println(locale);

        Object obj = 2.2D;
        System.out.println(new BigDecimal(obj.toString()));
        // System.out.println(new BigDecimal((String) obj)); // 空指针异常

        obj = null;
        System.out.println(obj.toString()); // 空指针异常
    }

}
