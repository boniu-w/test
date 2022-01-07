package wg.application;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import wg.application.util.StringUtil;
import wg.application.util.WgUtil;

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

        String a = "SY/T 6477-2017";
        String b = "SY/T 6477";
        System.out.println(a.contains(b));

        String sss = "   ";
        String bbb = "";
        System.out.println(sss.equals(bbb)); // false

    }

    @Test
    public void test3() {
        String[] s = new String[0];
        System.out.println(Arrays.toString(s)); // []

        Locale locale = LocaleContextHolder.getLocale();
        System.out.println(locale); // zh_CN

        Object obj = 2.2D;
        System.out.println(new BigDecimal(obj.toString())); // 2.2
        // System.out.println(new BigDecimal((String) obj)); // 空指针异常

        obj = null;
        System.out.println(obj.toString()); // 空指针异常
    }

    @Test
    public void test4() {
        int i = 1;
        Integer integer = Integer.valueOf(Integer.toString(i));
        System.out.println(integer);

        String s = "      ";
        System.out.println(s.length());
        System.out.println(StringUtils.isEmpty(s));
        System.out.println(s.trim().length());
        System.out.println(StringUtils.isEmpty(s.trim()));
    }

    /************************************************************************
     * @description: string 的 最大长度
     * @author: wg
     * @date: 14:35  2021/11/19
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void testLength() {
        int len = 65534;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            // chars[i] = (char) i;
            chars[i] = (char) (48 + i);
        }
        String s = new String(chars);
        String[] strings = WgUtil.subStringByFixedLength(s, 10000);

        for (int i = 0; i < strings.length; i++) {
            System.out.println(strings[i]);
        }

        // System.out.println(String.format("%3s%n", s));

    }

    /************************************************************************
     * @description:
     * @author: wg
     * @date: 10:36  2022/1/4
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void slashTest() {
        String slash = StringUtil.slashPattern("7  ");
        System.out.println(slash);
    }
}
