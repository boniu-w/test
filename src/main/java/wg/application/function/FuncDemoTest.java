package wg.application.function;

/**
 * @author wg
 * @description 函数式接口例子
 * @date 2026/8/5 17:04
 */
public class FuncDemoTest {
    /**
     * @param
     * @return
     * @description
     * @author wg
     * @date 2026/8/5 17:05
     */
    public static void main(String[] args) {
        FuncDemo add = (a, b, c) -> a + b + c;
        System.out.println("求和: " + add.demo(1.0, 2.0, 3.0)); // 输出: 6.0

        FuncDemo average = (a, b, c) -> (a + b + c) / 3;
        System.out.println("平均值: " + average.demo(1.0, 2.0, 3.0)); // 输出: 2.0

        FuncDemo max = (a, b, c) -> Math.max(a, Math.max(b, c));
        System.out.println("最大值: " + max.demo(1.0, 5.0, 3.0)); // 输出: 5.0

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 传入求和逻辑
        double sum = calculate(10, 20, 30, (a, b, c) -> a + b + c);
        System.out.println("传入求和: " + sum); // 输出: 60.0

        // 传入自定义逻辑：(a + b) * c
        double custom = calculate(10, 20, 30, (a, b, c) -> (a + b) * c);
        System.out.println("自定义逻辑: " + custom); // 输出: 900.0

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FuncDemo demo = (a, b, c) -> a + b + c;

        // 1. 调用 default 方法（通过实例对象调用）
        System.out.println("default方法: " + demo.demo2()); // 输出: 0.0

        // 2. 调用 static 方法（通过接口名调用，和 Lambda 实例无关）
        System.out.println("static方法: " + FuncDemo.demo3()); // 输出: 1.0

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FuncDemo multiplyFunc = MathUtils::multiply;
        System.out.println("方法引用求积: " + multiplyFunc.demo(2, 3, 4)); // 输出: 24.0
    }

    // 定义一个通用的计算方法，接收 FuncDemo 作为参数
    public static double calculate(double a, double b, double c, FuncDemo func) {
        return func.demo(a, b, c);
    }

    private static class MathUtils {
        public static double multiply(double a, double b, double c) {
            return a * b * c;
        }
    }

}
