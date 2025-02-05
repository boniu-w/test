package wg.application;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import wg.application.entity.User;
import wg.application.math.FloatTest;
import wg.application.util.CommonUtil;
import wg.application.util.MathUtil;
import wg.application.util.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

public class MathTest {

    @Test
    public void test11111() {
        double v1 = Math.round(9.233317949991231E-5 * 10000) / 10000d;
        System.out.println("v1 = " + v1);

        Math.log(33);

        double kp0 = 209.2;
        double kp1 = 226;
        double e0 = -7.49;
        double e1 = -8.93;

        double v = (e1 - e0) / (kp1 - kp0);
        double atan = Math.atan(v);
        System.out.println("atan = " + atan); // -0.08550529367820456
    }

    @Test
    public void test() {
        double a = 0;
        double b = 0.0;
        System.out.println(a == b); // true
        System.out.println(b != 0); // false

        // scale 小数点后的位数
        double d = 2.22D;
        BigDecimal dd = new BigDecimal(d);
        int scale = dd.scale();
        System.out.println(dd); // 2.220000000000000195399252334027551114559173583984375
        System.out.println(scale); // 51

        double i = -2.22D;
        BigDecimal ii = new BigDecimal(i);
        System.out.println(ii);  // -2.220000000000000195399252334027551114559173583984375
        System.out.println(ii.scale()); // 51

        BigDecimal ss = new BigDecimal("2.22");
        System.out.println(ss.scale());

        // signum 正负号
        System.out.println(dd.signum()); // 1
        System.out.println(ii.signum()); // -1

        int i1 = 10;
        double i2 = 3;
        double v = i1 / i2;
        System.out.println("v = " + v); // v = 3.3333333333333335

        Integer integer = new Integer(798791);
        int aaa = 798791;
        boolean b1 = integer == aaa;
        System.out.println("integer == aaa " + b1); // true

        Integer integer1 = new Integer(798791);
        boolean b2 = integer == integer1;
        System.out.println("b2 = " + b2); // false

        int i3 = new BigDecimal("0.96").intValue();
        System.out.println("0.96 的 intvalue = " + i3); // 0
    }

    /************************************************************************
     * @description: 科学计数法
     * @author: wg
     * @date: 9:52  2021/12/13
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void test1() {
        String a = "5.63589065441568E-4";
        double v = Double.parseDouble(a);
        System.out.println(v);

        double v1 = 5.63589065441568E-4; // 能够识别 科学计数法

        String notation = CommonUtil.double2ScientificNotation(v);
        System.out.println(notation);

        String s = CommonUtil.double2ScientificNotation(0.00005D);
        System.out.println(s);

        String s1 = CommonUtil.double2ScientificNotation(0.0005D);
        System.out.println(s1);

        v = 0.0005D;

        System.out.println(v < 0.0001);

        boolean isNumber = StringUtil.isNumber("5.63589065441568E-40000");
        System.out.println("isNumber = " + isNumber); // true double=0

        boolean isNumber1 = StringUtil.isNumber("5.63589065441568E+40000");
        System.out.println("isNumber1 = " + isNumber1); // true double=Infinity

        boolean isNumber2 = StringUtil.isNumber("-5.63589065441568E+40000");
        System.out.println("isNumber2 = " + isNumber2); // true double=-Infinity

        double aaa = 0.00000028162103058754967;
        String s2 = MathUtil.double2ScientificNotation(aaa);
        System.out.println("s2 = " + s2); // s2 = 2.816E-7

        double aaa1 = 0.028162103058754967;
        String s21 = MathUtil.double2ScientificNotation(aaa1);
        System.out.println("s21 = " + s21); // s21 = 0.028

        double aaa12 = 0.0028162103058754967;
        String s212 = MathUtil.double2ScientificNotation(aaa12);
        System.out.println("s212 = " + s212); // s212 = 0.003

        double aaa123 = 0.00028162103058754967;
        String s2123 = MathUtil.double2ScientificNotation(aaa123);
        System.out.println("s2123 = " + s2123); // s2123 = 2.816E-4

        double aaa1231 = 4.222803117922996;
        String s21231 = MathUtil.double2ScientificNotation(aaa1231);
        System.out.println("s21231 = " + s21231); // s21231 = 4.223

        double aaa12311 = 0.30000000000000004;
        String s212311 = MathUtil.double2ScientificNotation(aaa12311);
        System.out.println("s212311 = " + s212311); // s212311 = 0.3


        double aaa123111 = 0.00001;
        String s2123111 = MathUtil.double2ScientificNotation(aaa123111);
        System.out.println("s2123111 = " + s2123111); // s2123111 = 1.000E-5
    }

    @Test
    public void test2() {
        double d = 4.9E-324D;
        System.out.println(d);
        System.out.println(new BigDecimal(d).toPlainString());

        double d1 = 1.7976931348623157E308D;
        System.out.println(new BigDecimal(d1).toPlainString());

        String a = "5.63589065441568E-4";
        double v = Double.parseDouble(a);
        System.out.println(v);

        String s = CommonUtil.double2ScientificNotation(v);
        System.out.println(s);
    }

    /************************************************************************
     * @description: 判断是否是数字
     * @author: wg
     * @date: 17:11  2021/12/14
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void testIsNumber() {
        boolean number = CommonUtil.isNumber("-1.00000000000E-4");
        System.out.println(number);
    }

    /************************************************************************
     * @description: 是否是整数
     * @author: wg
     * @date: 17:11  2021/12/14
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void testIsInteger() {
        boolean number = CommonUtil.isInteger("0.001");
        System.out.println(number);

        int a = -10000;
        boolean b = a < -9999;
        if (b) {
            System.out.println(b);
        }
    }

    /************************************************************************
     * @description: bit
     * @author: wg
     * @date: 16:07  2021/12/20
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void testBit() {
        String str = "-100";
        byte[][] bytes = MathUtil.stringToBit(str);

        // [0, 0, 1, 0, 1, 1, 0, 1]
        // [0, 0, 1, 1, 0, 0, 0, 1]
        // [0, 0, 1, 1, 0, 0, 0, 0]
        // [0, 0, 1, 1, 0, 0, 0, 0]
        for (byte[] aByte : bytes) {
            System.out.println(Arrays.toString(aByte));
        }

        System.out.println("----------");

        byte b = -100;
        byte[] bit = MathUtil.byteToBit(b);
        System.out.println(Arrays.toString(bit)); // [1, 0, 0, 1, 1, 1, 0, 0]

        System.out.println("-------------");

    }

    @Test
    public void testFloat() {
        FloatTest test01 = new FloatTest();
        test01.test02();
    }

    @Test
    public void radix() {
        int l = Integer.parseInt("12", 3);
        System.out.println("表明 参数 是 3进制数 , 转成十进制后是: " + l);  //

        String s = Integer.toString(10, 3);
        System.out.println("任意10进制数 转化成任意进制 : " + s);

        int i = 1 % 3;
        System.out.println(i);

        int wo = 5201314;
        String s1 = Integer.toString(5201314, 16);
        System.out.println(s1);

        int i1 = Integer.parseInt("4f5da2", 16);
        System.out.println(i1);
    }

    @Test
    public void float2Binary() {
        FloatTest floatTest = new FloatTest();
        floatTest.float2Binary();
    }

    /************************************************************************
     * @description: nan
     * @author: wg
     * @date: 11:20  2021/12/30
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void testNan() {
        double naN = Double.NaN;
        System.out.println("naN = " + naN);
        double a = 0;
        double b = 0;
        double c = a / b;
        System.out.println(c); // NaN

        System.out.println(Double.isNaN(c)); // true
        System.out.println(Double.isInfinite(c)); // false
        System.out.println(Double.isFinite(c)); // false

        System.out.println(((Object) c).toString()); // NaN
        BigDecimal bigDecimal = new BigDecimal(c); // java.lang.NumberFormatException: Infinite or NaN
        System.out.println(bigDecimal);

        double s = Double.NaN;
    }

    /************************************************************************
     * @description: 最长8个数字位
     * @author: wg
     * @date: 15:05  2021/12/31
     * @params:
     * @return:
     ************************************************************************/
    @Test
    public void floatTest() {
        float a = 1.0000001f;
        float b = 1.00000016f;
        float c = 1000.0000001f;
        float d = 1000.0001f;
        float e = 1000.00016f;
        float f = 1000000.11f;
        float ff = 1000000.15f;
        float g = 10000000.11f;

        System.out.println(a); // 1.0000001
        System.out.println(b); // 1.0000001
        System.out.println(c); // 1000.0
        System.out.println(d); // 1000.0001
        System.out.println(e); // 1000.0002
        System.out.println(f); // 1000000.1
        System.out.println(ff); // 1000000.1
        System.out.println(g); // 1.0E7

        float maxValue = Float.MAX_VALUE;
        System.out.println("maxValue = " + maxValue); // 3.4028235E38
    }

    /************************************************************************
     * @author: wg
     * @description: 乘除
     * @params:
     * @return:
     * @createTime: 13:57  2022/5/12
     * @updateTime: 13:57  2022/5/12
     ************************************************************************/
    @Test
    public void multiplyTest() {
        int a = 123;
        int b = a << 1;

        System.out.println(b); // 246

        boolean f = b >> 1 == a;
        System.out.println(f); // true

        System.out.println(a >> 1); // 61
    }

    @Test
    public void powTest() {
        long s = MathUtil.pow(2, 3);
        System.out.println(s);
    }

    /**
     * null +=
     */
    @Test
    public void nullPlus() {
        Integer i = null;

        i += 1;
        System.out.println(i); // java.lang.NullPointerException
    }

    @Test
    public void arithmeticTest() {
        double a = 0.9;
        double b = 0.8;

        double v = a + b;
        System.out.println(v); // 1.7000000000000002

        // ResultSet

        double temp = a;
        a = b;
        b = temp;

        System.out.println(a + " " + b); // 0.8 0.9
    }

    /************************************************************************
     * @author: wg
     * @description: 移位运算 优先级 并不比加减运算高
     * @params:
     * @return:
     * @createTime: 15:35  2022/6/28
     * @updateTime: 15:35  2022/6/28
     ************************************************************************/
    @Test
    public void testYiwei() {
        int i = (1 << 2) - 1 << 2;
        System.out.println(i);

        int j = 1 << 2 - 1 << 2;
        System.out.println(j);

        int a = 100;
        int i1 = a >> 3;
        System.out.println(i1);

        System.out.println(1 << 31);
        System.out.println((1 << 31) - 1);
    }

    /************************************************************************
     * @author: wg
     * @description: 异或: 相同为0，不同为1
     * @params:
     * @return:
     * @createTime: 15:51  2022/7/8
     * @updateTime: 15:51  2022/7/8
     ************************************************************************/
    @Test
    public void testYiHuo() {
        // int a = 2;
        // int b = 4;
        //
        // // int i1 = a ^ b;
        // // System.out.println(i1); // 6
        // //
        // // int i = a ^ b ^ a;
        // // System.out.println(i); // i = 4
        //
        // // 交换值, 而不用中间变量
        // a = a ^ b; // a1 = a^b // 6
        // b = b ^ a; // b = b^a1 = b^a^b = a // 2
        // a = a ^ b; // a = a^b^a = b  // 4
        //
        // System.out.printf("a= %d, b= %d", a, b);
        // System.out.println();

        // 清晰版
        int a = 2;
        int b = 4;

        int i = a ^ b ^ a; // i=b=4
        int j = a ^ b ^ b; // j=a=2 此步省略
        int k = i ^ a ^ b; // k=a=2

        // 最后 i=b, k=a
        System.out.println(i);
        System.out.println(k);
    }

    /************************************************************************
     * @author: wg
     * @description: 与 0 异或
     * 结论: 任何数与0异或, 都是他本身
     * @params:
     * @return:
     * @createTime: 13:57  2023/1/5
     * @updateTime: 13:57  2023/1/5
     ************************************************************************/
    @Test
    public void testXOR() {
        int a = 2;
        int b = 4;

        // 与 0 异或
        int c = 0;
        int i2 = a ^ c;
        int i3 = b ^ c;

        System.out.println(i2); // 2
        System.out.println(i3); // 4
    }

    /************************************************************************
     * @author: wg
     * @description: `&` (与运算): 两位同时为“1”，结果才为“1”，否则为0
     * `|` (或运算) : 参加运算的两个对象只要有一个为1，其值为1。
     * @params:
     * @return:
     * @createTime: 16:46  2022/6/30
     * @updateTime: 16:46  2022/6/30
     ************************************************************************/
    @Test
    public void testHuo() {
        int a = 2;
        int b = 3;
        int i = a | b;
        System.out.println(i);

        int c = 0x07;

        int i1 = a & c;
        System.out.println(i1);
    }

    /************************************************************************
     * @author: wg
     * @description: bigdecimal equals
     * @params:
     * @return:
     * @createTime: 14:35  2022/8/2
     * @updateTime: 14:35  2022/8/2
     ************************************************************************/
    @Test
    public void bigDecimalEquals() {
        BigDecimal a = new BigDecimal("0.00");
        Double b = 0D;

        boolean equals = Objects.equals(a.doubleValue(), b); // true
        System.out.println(equals); // true

        boolean b1 = a.doubleValue() == 0; // true
        System.out.println(b1); // true

        double a1 = 0d;
        Double a2 = 0.000D;
        System.out.println("a1==a2 = " + (a1 == a2)); // true
        // System.out.println("a1.toString() = " + a1.toString()); // 报错
        BigDecimal bigDecimal = BigDecimal.valueOf(a2);
        System.out.println("bigDecimal = " + bigDecimal); // 0.0
    }

    @Test
    public void getRandomString() {
        String s = RandomStringUtils.randomAlphanumeric(32);
        System.out.println(s); // jFGa1aBUIYO5SmJntxc59tDZY1RMNrnd

        String ascii = RandomStringUtils.randomAscii(32);
        System.out.println("ascii = " + ascii); // ascii = I4<CVXUa<?!MRRZ}=j\"h&bl+)x:?3yr
    }

    /************************************************************************
     * @author: wg
     * @description: double 可以 强转为 int
     * @params:
     * @return:
     * @createTime: 11:16  2022/11/15
     * @updateTime: 11:16  2022/11/15
     ************************************************************************/
    @Test
    public void testTransform() {
        int randomFour = (int) ((Math.random() * 9 + 1) * 1000);
        System.out.println(randomFour);
        double random = Math.random();
        System.out.println(random);

        int random1 = (int) random;
        System.out.println(random1);
    }

    /************************************************************************
     * @author: wg
     * @description:
     * 描述: 两个 integer == 问题
     * 结论: 对象判断 == 时, 用 equals
     * 说明: 下面我们就来详细解释一下，为什么是上面的结果。（下面的序号就是对应的是上面的答案序号）
     *
     * 1、int1 == int2 为true，这个我就讲了，这个都知道
     *
     * 2、int1 == integer1，Integer是int的封装类，当Integer与int进行==比较时，Integer就会拆箱成一个int类型，所以还是相当于两个int类型
     * 进行比较，这里的Integer,不管是直接赋值，还是new创建的对象，只要跟int比较就会拆箱为int类型，所以就是相等的。
     *
     * 3、integer1 == integer2 -> false，这是两个都是对象类型，而且不会进行拆箱比较，所以不等
     *
     * 4、integer3 == a1 -> false , integer3是一个对象类型，而a1是一个常量它们存放内存的位置不一样，所以也不等，具体存在内存的位置看以看
     * 文章：https://blog.csdn.net/OyangYujun/article/details/41173747
     *
     * 5、6   看起来是一模一样的为什么一个是true，一个是false，这是因为Integer作为常量时，对于-128到127之间的数，会进行缓存，
     * 也就是说int a1 = 127时,在范围之内，这个时候就存放在缓存中，当再创建a2时，java发现缓存中存在127这个数了，就直接取出来赋值给a2，
     * 所以a1 == a2的。当超过范围就是new Integer()来new一个对象了，所以a、b都是new Integer(128)出来的变量，所以它们不等。
     *
     * @params:
     * @return:
     * @createTime: 17:27  2022/12/1
     * @updateTime: 17:27  2022/12/1
     ************************************************************************/
    @Test
    public void testInteger() {
        int int1 = 12;
        int int2 = 12;
        int int4 = 128;

        Integer integer1 = new Integer(12);
        Integer integer2 = new Integer(12);
        Integer integer3 = new Integer(127);
        Integer integer4 = new Integer(128);

        Integer a1 = 127;
        Integer a2 = 127;

        Integer a = 128;
        Integer b = 128;

        System.out.println("int1 == int2 -> " + (int1 == int2)); // true
        System.out.println("int1 == integer1 -> " + (int1 == integer1)); // true
        System.out.println("integer1 == integer2 -> " + (integer1 == integer2)); // false
        System.out.println("integer3 == a1 -> " + (integer3 == a1)); // false
        System.out.println("a1 == a2 -> " + (a1 == a2)); // true
        System.out.println("a == b -> " + (a == b)); // false

        Integer b1 = 128;
        Integer b2 = new Integer(128);

        System.out.println("b1 == b2 -> " + (b1 == b2)); // false
        System.out.println("(b == b1) = " + (b == b1)); // false

        System.out.println("int4==integer4 = " + (int4 == integer4)); // true
    }

    @Test
    public void testObj() {
        boolean equals = Objects.equals("1.6", "1.60");
        System.out.println(equals); // false

        Double b = new Double(0.00);
        double a = 0;
        System.out.println(b.equals(a)); // true

        Double c = new Double(0);
        System.out.println(b.equals(c)); // true

        Boolean bo1 = new Boolean(true);
        Boolean bo2 = new Boolean(false);
        boolean bb = true;

        System.out.println("bb==bo1 = " + (bb == bo1)); // true

        User user = new User();
        user.setAge(1288);

        System.out.println("user.getAge() ==1288 = " + (user.getAge() == 1288)); // true
    }

    /************************************************************************
     * @author: wg
     * @description: 初始化数组时, 默认值
     * @params:
     * @return:
     * @createTime: 16:32  2023/3/15
     * @updateTime: 16:32  2023/3/15
     ************************************************************************/
    @Test
    public void testInit() {
        double[] doubles = new double[3];
        System.out.println(doubles[0]);
        System.out.println(doubles[1]);
        System.out.println(doubles[2]);

        double a = 1.111;
        System.out.println(a);
    }

    /************************************************************************
     * @author: wg
     * @description: 逻辑运算
     * @params:
     * @return:
     * @createTime: 9:57  2023/7/28
     * @updateTime: 9:57  2023/7/28
     ************************************************************************/
    @Test
    public void logicMath() {
        int i = 0;
        if ((++i == 1 || ++i == 2) || (++i == 3 && ++i == 4)) {
            System.out.println(i); // 1
        }

        int j = 0;
        if ((++j == 33 || ++j == 22) || (++j == 3 && ++j == 4)) {
            System.out.println(j); // 4
        }

        int k = 0;
        int m = ++k;
        System.out.println(m); // 1
        System.out.println(k); // 1
    }

    @Test
    public void bigdecimalTest() {
        BigDecimal bigDecimal = new BigDecimal("3.53000");
        BigDecimal bigDecimal1 = bigDecimal.stripTrailingZeros();

        System.out.println("bigDecimal1 = " + bigDecimal1); // 3.33

        BigDecimal bigDecimal2 = new BigDecimal("3.0000000");
        BigDecimal bigDecimal3 = bigDecimal2.stripTrailingZeros();

        System.out.println("bigDecimal1 = " + bigDecimal3); // 3

        System.out.println("bigDecimal.toEngineeringString() = " + bigDecimal.toEngineeringString());

        BigInteger bigInteger = bigDecimal.toBigInteger();
        System.out.println("bigInteger = " + bigInteger);

    }

    @Test
    public void isInteger() {
        String a = "11.11";
        String a1 = "0";
        String a2 = "sdf";
        String a3 = "2200000000";

        boolean integer = StringUtil.isInteger(a);
        System.out.println("integer = " + integer); // false;

        System.out.println("StringUtil.isInteger(a1) = " + StringUtil.isInteger(a1)); // true
        System.out.println("StringUtil.isInteger(a2) = " + StringUtil.isInteger(a2)); // false
        System.out.println("StringUtil.isInteger(a3) = " + StringUtil.isInteger(a3)); // false
    }

    /************************************************************************
     * @author: wg
     * @description: 虚数
     * @params:
     * @return:
     * @createTime: 9:41  2023/10/20
     * @updateTime: 9:41  2023/10/20
     ************************************************************************/
    @Test
    public void sqrt() {
        int a = 3;
        int a1 = 4;
        int i = a - a1;
        double aDouble = (double) i;
        double sqrt = Math.sqrt(aDouble);
        System.out.println("sqrt = " + sqrt); // sqrt = NaN
    }

    @Test
    public void testtest() {
        BigDecimal bcr = new BigDecimal("0.4");
        BigDecimal probeCorrosionRate = new BigDecimal("0.09");
        BigDecimal decimal = new BigDecimal("1");
        System.out.println("decimal.scale() = " + decimal.scale()); // 0

        BigDecimal divide = probeCorrosionRate.divide(bcr, 4, RoundingMode.HALF_UP);
        System.out.println("divide = " + divide);
        BigDecimal subtract = new BigDecimal("1").subtract(probeCorrosionRate.divide(bcr, 4, RoundingMode.HALF_UP))
                .multiply(new BigDecimal("100")).setScale(4, RoundingMode.HALF_UP);

        System.out.println("subtract = " + subtract);
    }

    /**
     * @author: wg
     * @description: 移位
     * @params:
     * @return:
     * @createTime: 11:37  2024/1/4
     * @updateTime: 11:37  2024/1/4
     */
    public void transpose() {

        int a = 10;  // 二进制 1010

        int i = a << 1;
        System.out.println(i);  // 20 = a * 2的1次方
        System.out.println("20 的二进制表示 : " + Integer.toBinaryString(i));


        int i1 = a << 2;
        System.out.println(i1);  // 40 = a * 2的2次方


        int i2 = a << 3;
        System.out.println(i2);  // 80 = a * 2的3次方


        int i3 = a * (a ^ 3);  // ^  二进制的异或符
        System.out.println(i3);

        // a*3;
        int i4 = (a << 1) + a;
        System.out.println(i4);


        /* 除法 */
        int i5 = a >> 3;
        System.out.println(i5); // 1

        int i6 = (a >> 1) - a;
        System.out.println(i6);


        /* 与运算符用符号 "&" */
        int i7 = a & 2;
        int i9 = a % 4;
        System.out.println("i7 ->= " + i7 + "   ####  i9 ->= " + i9);  // 2

        int i8 = a & 1;
        int i10 = a % 2;
        System.out.println("i8 ->= " + i8 + "   ####  i10->  " + i10);  // 0
        System.out.println();

        int n = 1;
        int m = (n << 3) + 2;
        System.out.println("m - > " + m);
        System.out.println(Integer.toBinaryString(m));
        System.out.println();
        int l = Integer.parseInt("12", 3);
        System.out.println("表明 参数 是 3进制数 , 转成十进制后是:  -> " + l);  //
        int p = Integer.parseInt("1010", 2);
        System.out.println("表明 参数 是 2进制数 , 转成十进制后是:  -> " + p);  //

        // 负数的 左右 移位
        int k = -1;
        System.out.println("k 的二进制表示 : " + Integer.toBinaryString(k));
        int h = k << 1;
        System.out.println(h);
        System.out.println("h 的二进制表示 : " + Integer.toBinaryString(h));
    }

    /**
     * @author wg
     * @description qnorm 返回值是给定概率p后的下分位点 用java apache math3 怎么写
     * @createTime 10:36  2024/6/12
     * @updateTime 10:36  2024/6/12
     */
    @Test
    public void qnorm() {
        double qnorm = MathUtil.qnorm(0.92);

        double a = 2 * (3.0 + 2) / (2.0 + 1);
        System.out.println("a = " + a);
    }

    @Test
    public void test11() {
        double i = 1E5;
        System.out.println("i = " + i);
    }
}

