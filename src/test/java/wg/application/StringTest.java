package wg.application;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import wg.application.function.StringLength;
import wg.application.util.StringUtil;
import wg.application.util.CommonUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
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

    /************************************************************************
     * @author: wg
     * @description: string format 测试
     * @params:
     * @return:
     * @createTime: 11:17  2022/11/30
     * @updateTime: 11:17  2022/11/30
     ************************************************************************/
    @Test
    public void formatTest() {
        //+号的用法
        String str;
        str = String.format("数字的正负表示：%+d %d %+d %d", 8, 8, -8, -8);
        System.out.println(str);
        //-的用法
        str = String.format("左对齐：%-6d", 8);
        System.out.println(str);
        //0的用法
        str = String.format("缺位补零：%06d", 8);
        System.out.println(str);
        //' '空格的用法
        str = String.format("缺位补空格：% 6d", 8);
        System.out.println(str);
        str = String.format("缺位补空格：% 6d", -8);
        System.out.println(str);
        //,的用法
        str = String.format("数字分组：%,d", 123456789);
        System.out.println(str);
        //(的用法
        str = String.format("括号用法：%(d", -8888);
        System.out.println(str);
        str = String.format("括号用法：%(d", 8888);
        System.out.println(str);
        //#的用法
        str = String.format("#括号用法(十六进制)：%#x", 12);
        System.out.println(str);
        str = String.format("#括号用法(八进制)：%#o", 12);
        System.out.println(str);
        //<的用法
        str = String.format("<括号用法：%f %<3.1f", 3.14, 3.2);
        //"%<3.1f"作用的对象是前一个"%f"所作用的对象
        System.out.println(str);
        str = String.format("<括号用法：%f %<3.1f", 4.2, 9.01);
        System.out.println(str);
    }

    /************************************************************************
     * @author: wg
     * @description: 测试 integer 与 string equals
     * 结论: 可以比较
     * @params:
     * @return:
     * @createTime: 15:29  2022/11/30
     * @updateTime: 15:29  2022/11/30
     ************************************************************************/
    @Test
    public void testEquals() {
        Integer a = 1;
        String b = "sdf";

        boolean equals = a.equals(b);
        System.out.println(equals);

        System.out.println("\n");
        System.out.println("----");

        System.out.print("\n");
        System.out.print("000");
    }

    /************************************************************************
     * @author: wg
     * @description: 二进制字符串转十进制int
     * @params:
     * @return:
     * @createTime: 15:32  2023/1/6
     * @updateTime: 15:32  2023/1/6
     ************************************************************************/
    @Test
    public void test2Dec() {
        String a = "1111";
        int i = StringUtil.binaryString2DecimalInt(a);
        System.out.println(i);
    }

    /************************************************************************
     * @author: wg
     * @description:
     * 1. jsonArray alibaba
     * 2. list 平均值
     * 3. jsonObject alibaba
     * @params:
     * @return:
     * @createTime: 11:01  2023/1/10
     * @updateTime: 11:01  2023/1/10
     ************************************************************************/
    @Test
    public void testJsonArray() {
        String jsonString = "[{\"key\":2,\"sectionCode\":\"M2\",\"c1\":\"222\",\"min\":222,\"c2\":\"222\"},{\"key\":1,\"sectionCode\":\"M1\",\"min\":333,\"c1\":\"333\",\"c2\":\"333\"},{\"sectionCode\":\"最小值\",\"c1\":222,\"min\":222,\"c2\":222}]";
        JSONArray jsonArray = JSON.parseArray(jsonString);

        System.out.println(jsonArray);

        ArrayList<Double> mins = new ArrayList<>();
        ArrayList<Double> cs = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSON.parseObject(o.toString());
            Map<String, Object> innerMap = jsonObject.getInnerMap();
            if (innerMap.containsKey("key")) {
                double min = jsonObject.getDouble("min");
                mins.add(min);
            } else {
                double c1 = jsonObject.getDouble("c1");
                double c2 = jsonObject.getDouble("c2");
                cs.add(c1);
                cs.add(c2);
            }
        }

        double ma = mins.stream().mapToDouble(Double::valueOf).average().getAsDouble();
        double ca = cs.stream().mapToDouble(Double::valueOf).average().getAsDouble();

        System.out.println();
        System.out.println(ma);
        System.out.println(ca);
    }

    /************************************************************************
     * @author: wg
     * @description: 将字符串转为 sha256字符串
     * @params:
     * @return:
     * @createTime: 15:17  2023/2/28
     * @updateTime: 15:17  2023/2/28
     ************************************************************************/
    @Test
    public void testHash256() {
        String input = "hello world";
        String hash256 = StringUtil.hash256(input);
        System.out.println(hash256);
    }

    /************************************************************************
     * @author: wg
     * @description: function test
     * @params:
     * @return:
     * @createTime: 15:16  2023/2/28
     * @updateTime: 15:16  2023/2/28
     ************************************************************************/
    @Test
    public void testFunction() {
        StringLength stringLength = new StringLength();
        Integer length = stringLength.apply("hello");
    }

    /************************************************************************
     * @author: wg
     * @description:
     * @params:
     * @return:
     * @createTime: 11:30  2023/3/17
     * @updateTime: 11:30  2023/3/17
     ************************************************************************/
    @Test
    public void testObjectsEquals() {
        String a = "sdf";
        String b = "sdf";

        System.out.println(Objects.equals(a, b)); // true
    }
}
