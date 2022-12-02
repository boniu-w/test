package wg.application;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import wg.application.util.StringUtil;
import wg.application.util.CommonUtil;

import java.io.IOException;
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
        System.out.println(s.length()); // 6
        System.out.println(StringUtils.isEmpty(s)); // false
        System.out.println(s.trim().length()); // 0
        System.out.println(StringUtils.isEmpty(s.trim())); // true
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
        String[] strings = CommonUtil.subStringByFixedLength(s, 10000);

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

    /**
     * unicode
     */
    @Test
    public void unicodeTest() {
        String str = "zujie";

        String unicode = StringUtil.toUnicode(str);
        System.out.println(unicode);

        String decodeUnicode = StringUtil.decodeUnicode("\\u914D\\u7F6E");
        System.out.println(decodeUnicode);

        String s = StringUtil.decodeUnicode2(unicode);
        System.out.println(s);
    }

    @Test
    public void test11() {
        String filePath = "/pims-ld/components_water/edc0f89e483835c138d4a3698db8edc9ca564683d58ededa994c5bf4934f613e/103A3397-FCE1-4806-B38F-BB4EA8DFB630_4_5005_c.jpeg";

        // String[] split = filePath.split("/");
        // for (String s : split) {
        //     System.out.println(s);
        // }
        // System.out.println(split.length);
        //
        // StringBuilder stringBuilder = new StringBuilder("");
        // stringBuilder.append(split[2])
        //         .append("/")
        //         .append(split[3])
        //         .append("/")
        //         .append(split[4]);

        String objectName = "";
        objectName = filePath.split("/pims-ld")[1];
        System.out.println(objectName);
    }

    /************************************************************************
     * @author: wg
     * @description: 字符串的比较大小
     * @params:
     * @return:
     * @createTime: 10:17  2022/7/11
     * @updateTime: 10:17  2022/7/11
     ************************************************************************/
    @Test
    public void test12() {
        String a = "1";
        String b = "0100";

        int compareTo = a.compareTo(b);
        System.out.println(compareTo);


        System.out.println(Integer.valueOf(a));
        System.out.println(Integer.valueOf(b));

        String c = "-090";
        String substring = c.substring(c.lastIndexOf("-"));
        System.out.println(substring);
    }

    /************************************************************************
     * @author: wg
     * @description: 转 二进制 字符串
     * @params:
     * @return:
     * @createTime: 16:53  2022/6/30
     * @updateTime: 16:53  2022/6/30
     ************************************************************************/
    @Test
    public void toBinary() {
        int num = 7;

        String s = Integer.toBinaryString(num);
        System.out.println(s);
    }

    /************************************************************************
     * @author: wg
     * @description: asc
     * @params:
     * @return:
     * @createTime: 9:06  2022/9/19
     * @updateTime: 9:06  2022/9/19
     ************************************************************************/
    @Test
    public void testAsc() {
        String a = "\\347\\233\\221\\346\\265\\213\\346\\243\\200\\351\\252\\214\\347\\256\\241\\347\\220\\206";
        String[] split = a.split("\\\\");
        for (String s : split) {
            if (s.equals("")) continue;
            Character character = StringUtil.toChar(Integer.parseInt(s));
            System.out.println(character);
        }
    }

    /************************************************************************
     * @author: wg
     * @description: null 能转成 decimal 吗 -> 可以
     * @params:
     * @return:
     * @createTime: 11:04  2022/11/15
     * @updateTime: 11:04  2022/11/15
     ************************************************************************/
    @Test
    public void testNull() throws IOException {
        Object obj = null;
        BigDecimal bigDecimal = (BigDecimal) null;
        System.out.println(bigDecimal);

        String url = "https://search.jd.com/Search?keyword=手机&wq=手机&page=1";
        Document document = Jsoup.connect(url).get();
        String title = document.select("title").text();
        System.out.println(title);
    }
}
