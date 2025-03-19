package wg.application.util;

import java.util.function.Function;

/**
 * author wg
 * description FindRoots 解方程
 * createTime 16:11 2025/3/19
 * updateTime 16:11 2025/3/19
 */
public class FindRoots {

    static double root = 0d;

    public static double OfFunction(
            java.util.function.Function<Double, Double> func,
            double lowerBound,
            double upperBound,
            double tolerance) {
        // 实现根查找算法（例如二分法、牛顿法等）
        double mid = 0;
        int maxIterations = 100;
        if (BisectionTryFindRoot(func, lowerBound, upperBound, tolerance, maxIterations)) {
            return root;
        }
        return mid;
    }

    public static double OfFunction(
            java.util.function.Function<Double, Double> func,
            double lowerBound,
            double upperBound) {
        double mid = 0;
        double accuracy = 1E-08;
        int maxIterations = 100;
        if (BisectionTryFindRoot(func, lowerBound, upperBound, accuracy, maxIterations)) {
            return root;
        }
        return mid;
    }

    // Brent
    // public static boolean BrentTryFindRoot(
    //         java.util.function.Function<Double, Double> f,
    //         double lowerBound,
    //         double upperBound,
    //         double accuracy,
    //         int maxIterations) {
    //     double num1 = f.apply(lowerBound);
    //     double num2 = f.apply(upperBound);
    //     double num3 = num2;
    //     double num4 = 0.0;
    //     double num5 = 0.0;
    //     root = upperBound;
    //     double b = Double.NaN;
    //     if (Math.signum(num1) == Math.signum(num2))
    //         return false;
    //     for (int index = 0; index <= maxIterations; ++index) {
    //         if (Math.signum(num3) == Math.signum(num2)) {
    //             upperBound = lowerBound;
    //             num2 = num1;
    //             num5 = num4 = root - lowerBound;
    //         }
    //         if (Math.abs(num2) < Math.abs(num3)) {
    //             lowerBound = root;
    //             root = upperBound;
    //             upperBound = lowerBound;
    //             num1 = num3;
    //             num3 = num2;
    //             num2 = num1;
    //         }
    //         double PositiveDoublePrecision=2*Math.pow(2.0, -53.0);
    //         double a = PositiveDoublePrecision * Math.abs(root) + 0.5 * accuracy;
    //         double num6 = b;
    //         b = (upperBound - root) / 2.0;
    //         if (Math.abs(b) <= a || num3.AlmostEqualNormRelative(0.0, num3, accuracy))
    //             return true;
    //         if (b == num6)
    //             return false;
    //         if (Math.abs(num5) >= a && Math.abs(num1) > Math.abs(num3)) {
    //             double num7 = num3 / num1;
    //             double num8;
    //             double num9;
    //             if (lowerBound.AlmostEqualRelative(upperBound)) {
    //                 num8 = 2.0 * b * num7;
    //                 num9 = 1.0 - num7;
    //             } else {
    //                 double num10 = num1 / num2;
    //                 double num11 = num3 / num2;
    //                 num8 = num7 * (2.0 * b * num10 * (num10 - num11) - (root - lowerBound) * (num11 - 1.0));
    //                 num9 = (num10 - 1.0) * (num11 - 1.0) * (num7 - 1.0);
    //             }
    //             if (num8 > 0.0)
    //                 num9 = -num9;
    //             double num12 = Math.abs(num8);
    //             if (2.0 * num12 < Math.min(3.0 * b * num9 - Math.abs(a * num9), Math.abs(num5 * num9))) {
    //                 num5 = num4;
    //                 num4 = num12 / num9;
    //             } else {
    //                 num4 = b;
    //                 num5 = num4;
    //             }
    //         } else {
    //             num4 = b;
    //             num5 = num4;
    //         }
    //         lowerBound = root;
    //         num1 = num3;
    //         if (Math.abs(num4) > a)
    //             root += num4;
    //         else
    //             root += Sign(a, b);
    //         num3 = f.apply(root);
    //     }
    //     return false;
    // }

    private static double Sign(double a, double b) {
        return b < 0.0 ? (a < 0.0 ? a : -a) : (a < 0.0 ? -a : a);
    }

    // 二分法
    public static boolean BisectionTryFindRoot(
            Function<Double, Double> f,
            double lowerBound,
            double upperBound,
            double accuracy,
            int maxIterations
    ) {
        if (upperBound < lowerBound) {
            double num = upperBound;
            upperBound = lowerBound;
            lowerBound = num;
        }
        double num1 = f.apply(lowerBound);
        if (Math.signum(num1) == 0) {
            root = lowerBound;
            return true;
        }
        double num2 = f.apply(upperBound);
        if (Math.signum(num2) == 0) {
            root = upperBound;
            return true;
        }
        root = 0.5 * (lowerBound + upperBound);
        if (Math.signum(num1) == Math.signum(num2))
            return false;
        for (int index = 0; index <= maxIterations; ++index) {
            double num3 = f.apply(root);
            if (upperBound - lowerBound <= 2.0 * accuracy && Math.abs(num3) <= accuracy)
                return true;
            if (lowerBound == root || upperBound == root)
                return false;
            if (Math.signum(num3) == Math.signum(num1)) {
                lowerBound = root;
                num1 = num3;
            } else {
                if (Math.signum(num3) != Math.signum(num2))
                    return true;
                upperBound = root;
                num2 = num3;
            }
            root = 0.5 * (lowerBound + upperBound);
        }
        return false;
    }
}
