package wg.application.function;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MaxIterationsExceededException;
import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.analysis.solvers.BrentSolver;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import wg.application.controller.Test;

import javax.annotation.Resource;
import java.util.function.BiFunction;
import java.util.function.Function;

/************************************************************************
 * @description: 函数
 * Consumer 接受一个输入参数并且无返回值
 * Supplier 不接受任何参数，但返回一个结果
 * Function 接受一个参数并返回一个结果
 * BiFunction 接收2个参数, 返回一个结果
 * @author: wg
 * @date: 9:58  2021/9/18
 ************************************************************************/
public class FunctionTest {


    @Resource
    private static Test test;

    // public static void main(String[] args) {
    //     String s = "123";
    //     change(s, str -> {
    //         System.out.println(str);
    //         return Integer.parseInt(str);
    //     });
    //
    //     test();
    // }

    // public static void main(String[] args) {
    //     MyFunction myFunction = (str, num) -> {
    //         return str.length() > num;
    //     };
    //
    //     boolean hello = myFunction.test("hello", 3);
    //     System.out.println(hello);
    //
    //     Double apply = superficialVelocity().apply(1d, 2d);
    //     System.out.println("apply = " + apply);
    // }

    private static void change(String s, Function<String, Integer> function) {
        int i = function.apply(s);
        System.out.println(i);
    }

    private static void test() {
        Test test1 = new Test();
        test1.streamTest();
    }

    /**
     * @author wg
     * @description 由两个变量 算结果
     * @createTime 17:33  2024/8/8
     * @updateTime 17:33  2024/8/8
     */
    public static BiFunction<Double, Double, Double> superficialVelocity() {
        return (fluidVolumeFlow, crossSectionalArea) -> fluidVolumeFlow / crossSectionalArea;
    }

    public static void main(String[] args) throws MaxIterationsExceededException, FunctionEvaluationException {
        final double liquidA = 0.042354105747452506;
        final double liquidB = 0.0011560595601205585;
        double result = calcLiquidCompressibilityFactor(liquidA, liquidB);
        System.out.println("result = " + result);
    }

    public static double calcLiquidCompressibilityFactor(double liquidA, double liquidB) throws MaxIterationsExceededException, FunctionEvaluationException {
        UnivariateRealFunction func = z -> {
            return Math.pow(z, 3)
                    - (1 - liquidB) * Math.pow(z, 2)
                    + (liquidA - 3 * Math.pow(liquidB, 2) - 2 * liquidB) * z
                    - (liquidA * liquidB - Math.pow(liquidB, 2) - Math.pow(liquidB, 3));
        };

        double value = func.value(0.001227031744388285);
        System.out.println("value = " + value);

        BrentSolver brentSolver = new BrentSolver(1e-15);
        double solve = brentSolver.solve(func, 1E-8, 1, 1E-8);


        double lower = 1e-8;
        double upper = 0.3;
        UnivariateFunction func1 = z -> {
            return Math.pow(z, 3)
                    - (1 - liquidB) * Math.pow(z, 2)
                    + (liquidA - 3 * Math.pow(liquidB, 2) - 2 * liquidB) * z
                    - (liquidA * liquidB - Math.pow(liquidB, 2) - Math.pow(liquidB, 3));
        };

        /*functionValueAccuracy
        在创建 BrentSolver 实例时，代码 UnivariateSolver brentSolver = new BrentSolver(1e-10, 1e-14, 1000); 里的参数依次是 relativeAccuracy（相对精度）, absoluteAccuracy（绝对精度）、和 functionValueAccuracy（函数值精度）。
        functionValueAccuracy 指的是求解过程中允许的函数值误差范围。求解器的目标是找到使得函数 f(x) 的值尽可能接近 0 的 x 值。当求解过程中函数值 |f(x)| 小于 functionValueAccuracy 时，就认为找到了一个满足精度要求的根。
        例如，若 functionValueAccuracy 设置为 1e - 14，那么当某次迭代得到的函数值 f(x) 的绝对值小于 1e - 14 时，求解器就会认为找到了一个合适的根，从而停止迭代。它是对求解结果的函数值的一个精度约束，反映了求解结果在函数值层面上与理论根（函数值为 0）的接近程度。
        maxEval
        在调用 solve 方法时，代码 double solve = brentSolver.solve(1000, func, lower, upper); 中的 maxEval 是第一个参数。
        maxEval 代表求解过程中允许的最大函数评估次数。在使用数值方法求解方程根的过程中，求解器需要不断计算函数值来逼近根的位置。maxEval 限制了求解过程中调用目标函数 func 的最大次数。
        一旦求解过程中函数评估的次数达到了 maxEval，即使还没有找到满足精度要求的根，求解器也会停止迭代，并抛出相应的异常或者返回当前得到的近似解。它是对求解过程的一种限制，主要用于避免求解过程陷入无限循环或者耗时过长
        总结
        functionValueAccuracy：是关于求解结果的精度要求，用于判断找到的根是否满足精度要求，控制求解结果的函数值与 0 的接近程度。
        maxEval：是关于求解过程的限制条件，用于控制求解过程中函数评估的次数，避免求解过程无限制地进行下去。*/
       /* ‌相对精度‌：控制根值变化的相对误差，适合大根值。
        ‌绝对精度‌：控制根值的绝对区间宽度，适合小根值。
        ‌函数值精度‌：确保函数值接近零，独立于根的位置误差。
        ‌平衡三者‌：根据问题需求调整，避免过度迭代或过早终止*/
        UnivariateSolver brentSolver1 = new org.apache.commons.math3.analysis.solvers.BrentSolver(1e-10, 1e-14, 1000);
        try {
            double solve1 = brentSolver1.solve(1000, func1, lower, upper);
            return solve1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // return solve;
    }
}
