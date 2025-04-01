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
