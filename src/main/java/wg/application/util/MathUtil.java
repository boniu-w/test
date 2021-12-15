package wg.application.util;

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

        rex = "^[+-]?\\d*[Ee]*[+-]*\\d+$";
        boolean science = Pattern.matches(rex, val);
        if (science) {
            return science;
        }
        
        return false;
    }
}
