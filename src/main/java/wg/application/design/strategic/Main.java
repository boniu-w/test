package wg.application.design.strategic;

/************************************************************************
 * @author: wg
 * @description:
 * 1. 策略模式强调的是在不同算法之间的选择，主要是为了支持不同的策略。
 * 2. 工厂模式强调的是对象创建和实现的分离，主要是为了减少重复代码和实现细节。
 * 总结:
 * 策略模式解决的是如何在不同的算法之间进行选择，而工厂模式解决的是如何创建对象。同时，这两种模式也可以互相配合使用，比如使用工厂模式来创建策略对象。
 * @params:
 * @return:
 * @createTime: 14:53  2023/3/31
 * @updateTime: 14:53  2023/3/31
 ************************************************************************/
public class Main {
    public static void main(String[] args) {
        Context context = new Context(new AddStrategy());
        System.out.println("10 + 5 = " + context.executeStrategy(10, 5));

        context = new Context(new SubtractStrategy());
        System.out.println("10 - 5 = " + context.executeStrategy(10, 5));

        context = new Context(new MultiplyStrategy());
        System.out.println("10 * 5 = " + context.executeStrategy(10, 5));
    }
}
