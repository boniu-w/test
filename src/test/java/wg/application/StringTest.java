package wg.application;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import wg.application.entity.User;
import wg.application.enumeration.OrgEnum;
import wg.application.function.StringLength;
import wg.application.util.CommonUtil;
import wg.application.util.DateUtil;
import wg.application.util.StringUtil;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        System.out.println("s1 = " + s1); // null
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
            System.out.println("****8  " + s1.indexOf("混合")); // 0
        }

        if (s1.contains("混里")) {
            System.out.println("----- " + s1.indexOf("混里"));
        }

        String s2 = "2021-08-18 风险评价 10";
        String s3 = " 2021-08-18 风险评价 10";

        // System.out.println(s2.contains(s3.trim()));
        // System.out.println(s3.contains(s2.trim()));

        List<String> list = Collections.singletonList(s2); // 这个List中只能存放一个元素，多一个或者少一个都会导致异常。

        list = list.stream().filter(s -> s.contains(s3.trim())).collect(Collectors.toList());

        System.out.println(list);

        String a = "SY/T 6477-2017";
        String b = "SY/T 6477";
        System.out.println(a.contains(b)); // true

        String sss = "   ";
        String bbb = "";
        System.out.println(sss.equals(bbb)); // false

        String a1 = "12";
        String a2 = "18733";
        System.out.println("a1.contains(a2) = " + a1.contains(a2)); // false

        int i = a2.indexOf(a1);
        System.out.println(a2.indexOf(a1)); // -1

        String aaa = "0";
        int ccc = 0;
        System.out.println(Objects.equals(aaa, ccc)); // false
        System.out.println(aaa.equals(ccc)); // false
        System.out.println();
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
        System.out.println(integer); // 1

        String s = "      ";
        System.out.println(s.length()); // 6
        System.out.println(StringUtils.isEmpty(s)); // false
        System.out.println(s.trim().length()); // 0
        System.out.println(StringUtils.isEmpty(s.trim())); // true

        String a = null;
        String trim = a.trim(); // NullPointerException
        System.out.println(trim);
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
        System.out.println(slash); // /7
    }

    /**
     * unicode
     */
    @Test
    public void unicodeTest() {
        String str = "zujie";

        String unicode = StringUtil.toUnicode(str);
        System.out.println(unicode); // \u007a\u0075\u006a\u0069\u0065

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

        String path = "\\\\nas-wg\\wg\\影\\1994 饮食男女\\cover.jpg";
        String[] split = path.split("\\\\");
        Arrays.stream(split).forEach(System.out::println);
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
        System.out.println(compareTo); // 1


        System.out.println(Integer.valueOf(a)); // 1
        System.out.println(Integer.valueOf(b)); // 100

        String c = "-090";
        String substring = c.substring(c.lastIndexOf("-"));
        System.out.println(substring); // -090
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

        int i = num << 1;
        System.out.println("i = " + i);
        System.out.println("i.toBinaryString = " + Integer.toBinaryString(i));

        String a = "1100";
        int ia = StringUtil.binaryString2DecimalInt(a);
        System.out.println("ia = " + ia);

        String b = "10000";
        int ib = StringUtil.binaryString2DecimalInt(b);
        System.out.println("ib = " + ib);
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

        String b = "我";
        int i = b.codePointAt(0); // 会得到“我”字的Unicode编码
        System.out.println("i = " + i); // 25105
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
        System.out.println(bigDecimal); // null

        String url = "https://search.jd.com/Search?keyword=手机&wq=手机&page=1";
        Document document = Jsoup.connect(url).get();
        String title = document.select("title").text();
        System.out.println(title); // 手机 - 商品搜索 - 京东
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
        System.out.println(str); // +8 8 -8 -8
        //-的用法
        str = String.format("左对齐：%-6d", 8);
        System.out.println(str); // 8
        //0的用法
        str = String.format("缺位补零：%06d", 8);
        System.out.println(str); // 000008
        //' '空格的用法
        str = String.format("缺位补空格：% 6d", 8);
        System.out.println(str); //      8
        str = String.format("缺位补空格：% 6d", -8);
        System.out.println(str); //     -8
        //,的用法
        str = String.format("数字分组：%,d", 123456789);
        System.out.println(str); // 123,456,789
        //(的用法
        str = String.format("括号用法：%(d", -8888);
        System.out.println(str); // (8888)
        str = String.format("括号用法：%(d", 8888);
        System.out.println(str); // 8888
        //#的用法
        str = String.format("#括号用法(十六进制)：%#x", 12);
        System.out.println(str); // 0xc
        str = String.format("#括号用法(八进制)：%#o", 12);
        System.out.println(str); // 014
        //<的用法
        str = String.format("<括号用法：%f %<3.1f", 3.14, 3.2);
        //"%<3.1f"作用的对象是前一个"%f"所作用的对象
        System.out.println(str); // 3.140000 3.1
        str = String.format("<括号用法：%f %<3.1f", 4.2, 9.01);
        System.out.println(str); // 4.200000 4.2
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

        String title = "Key Equipment  Status";
        String title1 = "Key Equipment  Status";
        System.out.println("title1.equals(title) = " + title1.equals(title));
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

    /************************************************************************
     * @author: wg
     * @description: 序列化 测试
     * @params:
     * @return:
     * @createTime: 14:02  2023/4/13
     * @updateTime: 14:02  2023/4/13
     ************************************************************************/
    @Test
    public void testSerializable() throws IOException, ClassNotFoundException {
        User user = new User();
        user.setWealth(new BigDecimal("-99999.99"));
        // 序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(user);
        byte[] bytes = bos.toByteArray();

        // 反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bis);
        User myNewObject = (User) in.readObject();

        System.out.println(myNewObject.getWealth());  // 输出：-99999.99

    }

    @Test
    public void testSerializable1() throws IOException, ClassNotFoundException {
        String filePath = "e:/temp/person1.obj";
        User user = new User();
        user.setWealth(new BigDecimal("-99999.99"));

        try {
            //创建一个ObjectOutputStream输出流
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(filePath));
            //将对象序列化到文件filePath
            outStream.writeObject(user);

            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(filePath));
            User readObject = (User) inStream.readObject();
            System.out.println("反序列化后：" + readObject.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /************************************************************************
     * @author: wg
     * @description: 正则
     * @params:
     * @return:
     * @createTime: 15:02  2023/4/14
     * @updateTime: 15:02  2023/4/14
     ************************************************************************/
    @Test
    public void testRegex() {
        String regex = null;
        String regex1 = "\\d{4}(\\-|\\/|.)\\d{1,2}(\\-|\\/|.)\\d{1,2}$";
        String regex2 = "\\d{4}(\\-|\\/|.)\\d{1,2}$";
        String regex3 = "\\d{4}(\\-|\\/|.|年)\\d{1,2}(月*)$";
        String regex5 = "\\d{4}(年)?\\d{1,2}(月)?\\d?(日)?$";

        String patternStr1 = "yyyy/MM/dd";
        String patternStr2 = "yyyy/MM";
        String patternStr3 = "yyyy年MM月";

        String date1 = "2020/02/23";
        String date2 = "2005/01";
        String date3 = "2023年02月";

        Matcher matcher = Pattern.compile(regex1).matcher(date1);
        if (matcher.find()) {
            System.out.println(regex1);
            Date date = DateUtil.parseToDate(date1, patternStr1);
            System.out.println(date);
        }

        Matcher matcher1 = Pattern.compile(regex2).matcher(date2);
        if (matcher1.find()) {
            System.out.println(regex2);
            Date date = DateUtil.parseToDate(date2, patternStr2);
            System.out.println(date);
        }

        Matcher matcher3 = Pattern.compile(regex3).matcher(date3);
        if (matcher3.find()) {
            System.out.println(date3);
            Date date = DateUtil.parseToDate(date3, patternStr3);
            System.out.println(date);
        }
        
        /* 测试 regex3 的通用性  -- 是匹配的, 虽然匹配, 但为了转换成日期格式, 也就是为了匹配 patternstr3 , 不用 regex3,
        也就是为了一一对应, 改用 regex5 最为合适 */
        Matcher matcher4 = Pattern.compile(regex3).matcher(date2);
        if (matcher4.find()) {
            System.out.println(date2);
            Date date = DateUtil.parseToDate(date2, patternStr2);
            System.out.println(date);
        }

        // String regex5 = "\\d{4}(年)?\\d{1,2}(月)?\\d?(日)?$";
        String s5 = "20230701";
        Pattern pattern = Pattern.compile(regex5);
        Matcher matcher5 = pattern.matcher(s5);
        if (matcher5.find()) System.out.println("s5 = " + s5); // s5 = 20230701
    }

    @Test
    public void getBytes() {
        String a = "+F7Uq/z/1dsqMPGd0U3R4hhdpg8=";
        byte[] bytes = a.getBytes();
        System.out.println(Arrays.toString(bytes));
    }

    /************************************************************************
     * @author: wg
     * @description: 时间格式
     * @params:
     * @return:
     * @createTime: 9:19  2023/8/16
     * @updateTime: 9:19  2023/8/16
     ************************************************************************/
    @Test
    public void regexTest() {
        String regx1 = "^[0-9]{4}-[0-9]{1,2}$";
        String regx2 = "^[0-9]{4}/[0-9]{1,2}$";
        Pattern pattern1 = Pattern.compile(regx1);
        Pattern pattern2 = Pattern.compile(regx2);

        String monthParam = "2023-05";

        Matcher matcher1 = pattern1.matcher(monthParam);
        Matcher matcher2 = pattern2.matcher(monthParam);
        if (matcher1.find()) System.out.println("find"); // find
        if (matcher2.find()) System.out.println("matcher2.find() = " + matcher2.find()); // 没打印

        String reg = "^(T|t)he";
        String the1 = "the";
        String the2 = "The";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(the1);
        if (matcher.find()) System.out.println("the1 = " + the1); // the1 = the

        Matcher matcher3 = pattern.matcher(the2);
        if (matcher3.find()) System.out.println("the2 = " + the2); // the2 = The

        String dateStr = "2023-05";
        boolean b = DateUtil.isDate(dateStr);
        System.out.println("b = " + b); // b = true

        String dateStr1 = "2023";
        boolean b1 = DateUtil.isDate(dateStr1);
        System.out.println("b1 = " + b1); // b1 = false

        String dateStr2 = "20230504";
        boolean b2 = DateUtil.isDate(dateStr2);
        System.out.println("b2 = " + b2); // b2 = true

        String dateStr3 = "20230504   09:11:12";
        boolean b3 = DateUtil.isDate(dateStr3);
        System.out.println("b3 = " + b3); // b3 = true

        // 09092023 12:23:23
        String dateStr4 = "09092023 12:23:23";
        boolean b4 = DateUtil.isDate(dateStr4);
        System.out.println("b4 = " + b4); // b4 = true

        // 12:23:23 20230809
        String dateStr5 = "12:23:23 20230809";
        boolean b5 = DateUtil.isDate(dateStr5);
        System.out.println("b5 = " + b5); // b5 = false

        String dateStr6 = "12:23:23";
        boolean b6 = DateUtil.isDate(dateStr6);
        System.out.println("b6 = " + b6); // b6 = false

        System.out.println();

        System.out.println(String.valueOf(System.currentTimeMillis()).length());
        boolean b7 = DateUtil.isDate(String.valueOf(System.currentTimeMillis()));
        System.out.println("b7 = " + b7); // b7 = true

        String dateStr8 = "089879；23．20238768年123";
        boolean b8 = DateUtil.isDate(dateStr8);
        System.out.println("b8 = " + b8); // b8 = true

        String dateStr9 = "1";
        boolean b9 = DateUtil.isDate(dateStr9);
        System.out.println("b9 = " + b9); // b9 = false

        String dateStr10 = "Sun Sep 10 08:09:00 CST 2023";
        boolean b10 = DateUtil.isDate(dateStr10);
        System.out.println("b10 = " + b10); // b10 = true

        // 2023-08-16T09:20:15.066
        System.out.println(LocalDateTime.now());
        String pattern11 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        String dateStr11 = "2023-08-16T09:20:15.066";

        DateTimeFormatter formatter11 = DateTimeFormatter.ofPattern(pattern11);
        LocalDateTime localDateTime11 = LocalDateTime.parse(dateStr11, formatter11);
        System.out.println("localDateTime11 = " + localDateTime11);

        boolean b11 = DateUtil.isDate(dateStr11);
        System.out.println("b11 = " + b11); // true
    }

    @Test
    public void enumTest() {
        OrgEnum[] values = OrgEnum.values();
        for (OrgEnum value : values) {
            System.out.println("value.getCnName() = " + value.getCnName());
        }

        System.out.println(OrgEnum.IRAQ);
        System.out.println(OrgEnum.IRAQ.toString());

        System.out.println(OrgEnum.UK.toString().equals("UK"));

        OrgEnum uk = OrgEnum.valueOf("UK");
        System.out.println(uk);
    }

    /************************************************************************
     * @author: wg
     * @description: 字符串的内存地址
     * @params:
     * @return:
     * @createTime: 10:31  2023/9/21
     * @updateTime: 10:31  2023/9/21
     ************************************************************************/
    @Test
    public void testNewStr() {
        String a;
        // System.out.println(a); // 编译报错
        String b = null;
        System.out.println("b = " + b);

        String str = "str";
        String str1 = "str";
        String str2 = new String("str");

        System.out.println("str == str1 :" + (str == str1)); // true
        System.out.println("s" + "tr" == str); // true
        System.out.println("str" == str2); // false
        System.out.println("str==str2 = " + (str == str2)); // false

        User user = new User();
        user.setName(str);

        System.out.println("user.getName() == str1 = " + (user.getName() == str1)); // true

        User user1 = new User();
        user1.setName(str);

        System.out.println("user.getName() == user1.getName() = " + (user.getName() == user1.getName())); // true
    }

    /************************************************************************
     * @author: wg
     * @description: 大小写比较
     * @params:
     * @return:
     * @createTime: 17:23  2023/9/22
     * @updateTime: 17:23  2023/9/22
     ************************************************************************/
    @Test
    public void testCase() {
        String a = "UGA";
        String b = "uga";
        System.out.println(a.equals(b)); // false

        String c = "胜多负少";
        String upperCase = c.toUpperCase(Locale.ROOT);
        System.out.println("upperCase = " + upperCase); // 胜多负少
    }

    @Test
    public void testBigdecimalStringEquals() {
        BigDecimal bigDecimal = new BigDecimal("5.00");
        String a = "5";
        System.out.println(bigDecimal.toPlainString());
        System.out.println(bigDecimal.compareTo(new BigDecimal(a)));
    }

    /************************************************************************
     * @author: wg
     * @description: uuid test
     * @params:
     * @return:
     * @createTime: 10:57  2023/10/11
     * @updateTime: 10:57  2023/10/11
     ************************************************************************/
    @Test
    public void testUUID() {
        String u = "63bb2d8d-6233-489e-a0ce-a39452948e63";
        UUID uuid1 = UUID.fromString(u);

        System.out.println("uuid1.version() = " + uuid1.version()); // 4

        String a = null;
        String b = null;
        String a1 = a + b;
        String a2 = a + "-" + b;
        System.out.println("a1 = " + a1); // nullnull
        System.out.println("a2 = " + a2); // null-null

        System.out.println("A".equals("a")); // false

        u = null;
        boolean contains = u.contains("123");
        System.out.println("contains = " + contains);
    }
}
