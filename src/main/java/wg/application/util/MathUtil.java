package wg.application.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.Math.*;

public class MathUtil {

    /**
     * 把角秒换算成弧度
     *
     * @param seconds 角秒
     * @return 对应的弧度值
     */
    public static double secondsToRadians(double seconds) {
        return toRadians(secondsToDegrees(seconds));
    }

    /**
     * 把角度限制在[0, 2π]之间
     *
     * @param r 原角度(rad)
     * @return 转换后的角度(rad)
     */
    public static double mod2Pi(double r) {
        while (r < 0) {
            r += PI * 2;
        }
        while (r > 2 * PI) {
            r -= PI * 2;
        }
        return r;
    }

    /**
     * 把角度限制在[-π, π]之间
     *
     * @param r 原角度(rad)
     * @return 转换后的角度(rad)
     */
    public static double modPi(double r) {
        while (r < -PI) {
            r += PI * 2;
        }
        while (r > PI) {
            r -= PI * 2;
        }
        return r;
    }

    /**
     * 把角秒换算成角度
     *
     * @param seconds 角秒
     * @return 对应的弧度值
     */
    public static double secondsToDegrees(double seconds) {
        return seconds / 3600;
    }

    /**
     * 把度分秒表示的角度换算成度(deg)
     *
     * @param d 度
     * @param m 分
     * @param s 秒
     * @return 换算成度的值
     */
    public static double dmsToDegrees(int d, int m, double s) {
        return d + m / 60.0 + s / 3600;
    }

    /**
     * 把度分秒表示的角度换算成秒(arcsecond)
     *
     * @param d 度
     * @param m 分
     * @param s 秒
     * @return 换算成秒的值
     */
    public static double dmsToSeconds(int d, int m, double s) {
        return d * 3600 + m * 60 + s;
    }

    /**
     * 把度分秒表示的角度换算成弧度(rad)
     *
     * @param d 度
     * @param m 分
     * @param s 秒
     * @return 换算成弧度的值
     */
    public static double dmsToRadians(int d, int m, double s) {
        return toRadians(dmsToDegrees(d, m, s));
    }

    /**
     * 牛顿迭代求解方程的根
     *
     * @param f  方程表达式
     * @param x0 对根的估值
     * @return 在x0附近的一个根
     */
    public static double newtonIteration(Function f, double x0) {
        final double EPSILON = 1e-7;
        final double DELTA = 5e-6;
        double x;
        do {
            x = x0;
            double fx = f.f(x);
            double fpx = (f.f(x + DELTA) - f.f(x - DELTA)) / DELTA / 2;
            x0 = x - fx / fpx;
        } while (abs(x0 - x) > EPSILON);
        return x;
    }

    /************************************************************************
     * @description: 判断是否是数字
     * @author: wg
     * @date: 15:50  2021/12/14
     * @params:
     * @return:
     ************************************************************************/
    public static boolean isNumber(String val) {

        if (null == val || "".equals(val)) {
            return false;
        }

        String rex = "^[+-]?\\d*\\.?\\d*$";
        boolean numbMatch = Pattern.matches(rex, val);
        if (numbMatch) {
            return numbMatch;
        }

        // 科学计数法验证
        rex = "^[+-]?\\d+\\.?\\d*[Ee]*[+-]*\\d+$";
        boolean compile = Pattern.matches(rex, val);
        if (compile) {
            return compile;
        }
        return false;
    }

    /************************************************************************
     * @description: 是否是整数
     * "3." 也是整数
     * @author: wg
     * @date: 17:02  2021/12/14
     * @params:
     * @return:
     ************************************************************************/
    public static boolean isInteger(String val) {
        if (null == val || "".equals(val)) {
            return false;
        }

        String rex = "^[+-]?\\d*\\.?0*$";
        boolean numbMatch = Pattern.matches(rex, val);
        if (numbMatch) {
            return numbMatch;
        }

        // 科学计数法验证
        rex = "^[+-]?\\d*[Ee]*[+-]*\\d+$";
        boolean science = Pattern.matches(rex, val);
        if (science) {
            return science;
        }

        return false;
    }

    /************************************************************************
     * @description: byte -> bit (-128-127)
     * 字节 转 比特
     * 数组长度值为8，每个值代表bit，即8个bit。bit7 -> bit0
     * bit数组，bit7 -> bit0
     * @author: wg
     * @date: 15:38  2021/12/20
     * @params:
     * @return:
     ************************************************************************/
    public static byte[] byteToBit(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }

    /************************************************************************
     * @author: wg
     * @description: char -> int
     * @params:
     * @return:
     * @createTime: 10:37  2022/3/4
     * @updateTime: 10:37  2022/3/4
     ************************************************************************/
    public static int byteToInt(byte ch) {
        int val = 0;
        if (ch >= 0x30 && ch <= 0x39) {
            val = ch - 0x30;
        } else if (ch >= 0x41 && ch <= 0x46) {
            val = ch - 0x41 + 10;
        }
        return val;
    }

    /************************************************************************
     * @description: 字符串 转 bit
     * @author: wg
     * @date: 16:04  2021/12/20
     * @params:
     * @return:
     ************************************************************************/
    public static byte[][] stringToBit(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        byte[][] bits = new byte[bytes.length][8];
        for (int i = 0; i < bytes.length; i++) {
            byte[] bit = byteToBit(bytes[i]);
            bits[i] = bit;
        }
        return bits;
    }

    /************************************************************************
     * @author: wg
     * @description: 字节转16进制字符串
     * @params:
     * @return:
     * @createTime: 10:06  2022/3/3
     * @updateTime: 10:06  2022/3/3
     ************************************************************************/
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

    /************************************************************************
     * @author: wg
     * @description: 2的n次方
     * @params:
     * @return:
     * @createTime: 15:52  2022/4/8
     * @updateTime: 15:52  2022/4/8
     ************************************************************************/
    public static String pow2(int n) {
        StringBuilder res = new StringBuilder("1");
        // 重复N次
        for (int i = 0; i < n; i++) {
            // 进位标志，每轮清零
            int temp = 0;
            // result中的字符，从前往后逐位*2
            for (int j = res.length() - 1; j >= 0; j--) {
                // 乘法运算,需要加上进位
                temp = ((res.charAt(j) - '0') << 1) + temp / 10;
                // 替换此位结果
                res.setCharAt(j, (char) (temp % 10 + '0'));
            }
            // 产生进位则需添加新的数字
            if (temp / 10 >= 1)
                res.insert(0, '1');
        }

        return res.toString();
    }

    /************************************************************************
     * @author: wg
     * @description: a 的 b 次方
     * @params:
     * @return:
     * @createTime: 16:54  2022/5/17
     * @updateTime: 16:54  2022/5/17
     ************************************************************************/
    public static long pow(int a, int b) {
        long p = 1;
        for (int i = 1; i <= b; i++) {
            p *= a;
        }
        return p;
    }

    /************************************************************************
     * @author: wg
     * @description: 求对数
     * @params:
     * @return:
     * @createTime: 16:32  2022/4/8
     * @updateTime: 16:32  2022/4/8
     ************************************************************************/
    public static List<Integer> log2(Long val) {
        List<Integer> list = new ArrayList<>();
        int ind = 0;
        do {
            if ((val & 1) == 1) {
                list.add(ind);
            }
            ind++;
            val = val >> 1;
        } while (val > 0);
        return list;
    }

    /************************************************************************
     * @description: 转成科学计数法
     * @author: wg
     * @date: 16:04  2021/11/11
     * @params:
     * @return:
     ************************************************************************/
    public static String double2ScientificNotation(double num) {
        if (isInteger(String.valueOf(num))) {
            if (num > -9999 || num <= 9999) {
                return String.valueOf(((int) num));
            }
        }
        if (num < 0.001 || num > 1000) {
            String str = String.format("%E", num);//获取直接格式化结果
            str = str.replace("E-0", "E-");//将E-0N处理为E-N
            // 处理结果
            String temp = str.substring(0, str.indexOf("E"));
            // 精确到小数点后3位
            String f = String.format("%.3f", Double.parseDouble(temp));
            str = f + str.substring(str.indexOf("E"));
            return str;
        } else {
            return String.valueOf(new BigDecimal(String.valueOf(num)).setScale(3, RoundingMode.HALF_UP).doubleValue());
        }
    }

    /************************************************************************
     * @author: wg
     * @description: 转成科学计数法, 保留 scale 位
     * @params:
     * @return:
     * @createTime: 10:44  2022/11/30
     * @updateTime: 10:44  2022/11/30
     ************************************************************************/
    public static String double2ScientificNotation(double num, int scale) {
        if (isInteger(String.valueOf(num))) {
            if (num > -9999 || num <= 9999) {
                return String.valueOf(((int) num));
            }
        }
        if (num < 0.001 || num > 1000) {
            String str = String.format("%E", num);//获取直接格式化结果
            str = str.replace("E-0", "E-");//将E-0N处理为E-N
            // 处理结果
            String temp = str.substring(0, str.indexOf("E"));
            // 精确到小数点后 scale 位
            StringBuilder stringBuilder = new StringBuilder("");
            stringBuilder.append("%")
                    .append(".")
                    .append(scale)
                    .append("f");
            String f = String.format(stringBuilder.toString(), Double.parseDouble(temp));
            str = f + str.substring(str.indexOf("E"));
            return str;
        } else {
            return String.valueOf(new BigDecimal(String.valueOf(num)).setScale(3, RoundingMode.HALF_UP).doubleValue());
        }
    }

    /************************************************************************
     * @author: wg
     * @description: 计算 质数的个数 总和
     * @params:
     * @return:
     * @createTime: 16:51  2022/8/22
     * @updateTime: 16:51  2022/8/22
     ************************************************************************/
    public static void primeNumCount(int n) {
        long start = System.currentTimeMillis();    // 取开始时间
        // 质数 个数总和
        int sum = 0;
        // 1000万以内的所有质数
        // 用数组将1000万以内的数分为两大派系，质数用0代替数值，合数用1代替数值；
        // 一开始默认全部为质数，所以值全部为0，等到开始筛选的时候再把为合数的赋值为1
        int[] num = new int[n];
        num[0] = 1;          // 由于1规定不是质数，所以要提前用1标值
        // 根据埃氏筛法的结论，要得到自然数  N 以内的全部质数，必须把不大于" 二次根号  N "的所有质数的倍数剔除，剩下的就是质数
        double prescription = Math.sqrt(n);
        for (int i = 2; i <= prescription; i++) {
            // 开始把所有质数的倍数剔除，剩下的就是质数
            for (int j = i * i; j <= n; j += i) {
                // 从i*i开始去除，因为比i*i小的倍数，已经在前面去除过了
                // 例如：i=5
                // 5的2倍（10），3倍（15），在i=2的时候，已经去除过了

                num[j - 1] = 1;   // 把质数的倍数剔除，也就是赋值为1，不是质数就是合数
            }
        }
        // 遍历数组，把值为0的数全部统计出来，得到质数之和
        for (int j : num) {
            if (j == 0)
                sum++;
        }
        System.out.println(n + "以内的质数有" + sum + "个");
        long end = System.currentTimeMillis();
        System.out.println("The time cost is " + (end - start));
        System.out.println("");
    }

    /************************************************************************
     * @author: wg
     * @description: 判断是否是质数
     * @params:
     * @return:
     * @createTime: 16:51  2022/8/22
     * @updateTime: 16:51  2022/8/22
     ************************************************************************/
    public static boolean isPrimeNum(int num) {
        boolean flag = true;
        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /************************************************************************
     * @author: wg
     * @description: 1000 以内的质数
     * @params:
     * @return:
     * @createTime: 16:51  2022/8/22
     * @updateTime: 16:51  2022/8/22
     ************************************************************************/
    public static void test1() {
        //由于偶数中只有2是质数，此处直接将2的值进行输出，如下代码中查找质数时，只需考虑奇数即可
        System.out.print(2 + " ");
        OUT:
        //1不是质数，2是质数但是已经打印输出，因此循环中i的值从3开始即可，i+=2是因为在循环中我们不再考虑偶数
        for (int i = 3; i <= 1000; i += 2) {
            //请补充程序判断i是否是质数并打印i，如果是质数按照 System.out.print(i+" "); 格式进行打印
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    //如果i对j求余数等于0说明i不是质数
                    continue OUT;
                }
            }
            //说明i是质数
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) {
        boolean primeNum = isPrimeNum(31);
        System.out.println(primeNum);

        // primeNumCount(7);
        // test1();

        double a = -0.9128739127;
        String b = double2ScientificNotation(a, 14);
        System.out.println(b);
    }


}
