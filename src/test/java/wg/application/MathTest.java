package wg.application;

import org.junit.Test;
import wg.application.math.FloatTest;
import wg.application.util.MathUtil;
import wg.application.util.WgUtil;

import java.math.BigDecimal;
import java.util.Arrays;

public class MathTest {

    @Test
    public void test() {

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

        String notation = WgUtil.double2ScientificNotation(v);
        System.out.println(notation);

        String s = WgUtil.double2ScientificNotation(0.00005D);
        System.out.println(s);

        String s1 = WgUtil.double2ScientificNotation(0.0005D);
        System.out.println(s1);

        v = 0.0005D;

        System.out.println(v < 0.0001);
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

        String s = WgUtil.double2ScientificNotation(v);
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
    public void test3() {
        boolean number = WgUtil.isNumber("-1.00000000000E-4");
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
    public void test4() {
        boolean number = WgUtil.isInteger("0.001");
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
        byte[][] bytes = MathUtil.stringToBits(str);

        for (byte[] aByte : bytes) {
            System.out.println(Arrays.toString(aByte));
        }

        System.out.println("----------");

        byte b = -100;
        byte[] bit = MathUtil.byteToBitOfArray(b);
        System.out.println(Arrays.toString(bit));

        System.out.println("-------------");

    }

    @Test
    public void test03() {
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
        double a = 0;
        double b = 0;
        double c = a / b;
        System.out.println(c);

        System.out.println(Double.isNaN(c));
        System.out.println(Double.isInfinite(c));
        System.out.println(Double.isFinite(c));

        System.out.println(((Object) c).toString());
        BigDecimal bigDecimal = new BigDecimal(c); // java.lang.NumberFormatException: Infinite or NaN
        System.out.println(bigDecimal);

        double s = Double.NaN;
    }
}
