package wg.application.function;

import wg.application.controller.Test;

import javax.annotation.Resource;
import java.util.function.BiFunction;
import java.util.function.Function;

/************************************************************************
 * @description: 函数
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

    public static void main(String[] args) {
        MyFunction myFunction = (str, num) -> {
            return str.length() > num;
        };

        boolean hello = myFunction.test("hello", 3);
        System.out.println(hello);

        Double apply = superficialVelocity().apply(1d, 2d);
        System.out.println("apply = " + apply);
    }

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
}
