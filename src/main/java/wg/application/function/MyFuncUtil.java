package wg.application.function;

import java.util.function.Function;

/************************************************************************
 * author wg
 * description MyFuncUtil 
 * createTime 15:39 2024/8/8
 * updateTime 15:39 2024/8/8
 ************************************************************************/
public class MyFuncUtil {

    public static Function<Double, Double> multiple() {
        return x -> 2 * x;
    }

    public static void main(String[] args) {
        Function<Double, Double> multiple = multiple();
        Double apply = multiple.apply(5d);
        System.out.println("multiple = " + apply);
    }
}
