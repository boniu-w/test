package wg.application.java17;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class Java17Test {

    public static void main(String[] args) {
        testStrictfp();
        testStrictfp02();
        testRandom();
        // String s = testSwitch();
        // System.out.println(s);
    }

    /************************************************************************
     * @description: 严格浮点数
     * @author: wg
     * @date: 11:36  2021/12/23
     * @params:
     * @return:
     ************************************************************************/
    public strictfp static void testStrictfp() {
        float aFloat = 2.07F;
        double aDouble = 1D;
        double sum = aFloat - aDouble;
        System.out.println("sum: " + sum);
    }

    public static void testStrictfp02() {
        float aFloat = 2.07F * 100;
        double aDouble = 1D;
        double sum = aFloat - aDouble;
        System.out.println("sum: " + sum);
    }

    /************************************************************************
     * @description: 增强随机数
     * @author: wg
     * @date: 14:04  2021/12/23
     * @params:
     * @return:
     ************************************************************************/
    public static void testRandom() {
        RandomGeneratorFactory<RandomGenerator> l128X256MixRandom = RandomGeneratorFactory.of("L128X256MixRandom");
        // 使用时间戳作为随机数种子
        RandomGenerator randomGenerator = l128X256MixRandom.create(System.currentTimeMillis());
        for (int i = 0; i < 5; i++) {
            System.out.println(randomGenerator.nextInt(10));
        }

        // 遍历出所有的 PRNG 算法
        RandomGeneratorFactory.all().forEach(factory -> {
            System.out.println(factory.group() + ":" + factory.name());
        });

        l128X256MixRandom = RandomGeneratorFactory.of("Random");

        // 使用时间戳作为随机数种子
        randomGenerator = l128X256MixRandom.create(System.currentTimeMillis());
        for (int i = 0; i < 5; i++) {
            System.out.println(randomGenerator.nextInt(10));
        }
    }

    /************************************************************************
     * @description: switch
     * @author: wg
     * @date: 13:47  2021/12/23
     * @params:
     * @return:
     ************************************************************************/
    // public static String testSwitch() {
    //     Object obj = "123";
    //     if (obj instanceof String s) {
    //         System.out.println(s);
    //     }
    //     obj = "123%";
    //     return switch (obj) {
    //         case Integer i -> String.format("int %o", i); // 八进制
    //         case Long l -> String.format("long %x", l); // 十六进制
    //         case Double d -> String.format("double %e", d); // 指数型
    //         case String s -> String.format("string %s", s);
    //         default -> throw new IllegalStateException("Unexpected value: " + obj);
    //     };
    // }
}
