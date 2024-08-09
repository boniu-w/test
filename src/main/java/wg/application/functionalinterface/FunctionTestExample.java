package wg.application.functionalinterface;

import java.util.function.Function;

public class FunctionTestExample {

    /************************************************************************
     * @author: wg
     * @description:
     * 示例中，我们首先定义了一个FunctionTest接口实例toUpperCaseFunction，用于将字符串转换为大写。然后我们使用toUpperCaseFunction实例将输入字符串"hello"转换为大写，结果为"HELLO"。
     * 接下来，我们定义了一个Function接口实例reverseFunction，用于将字符串进行反转。然后我们使用toUpperCaseFunction的compose方法将toUpperCaseFunction和reverseFunction组合成一个新的函数composedFunction。这个新的函数首先将输入字符串转换为大写，然后再将结果进行反转。
     * 最后，我们使用组合后的函数composedFunction处理输入字符串"hello"，并得到最终的结果"OLLEH"，即先将字符串转换为大写"HELLO"，再将结果进行反转。
     * @params:
     * @return:
     * @createTime: 14:09  2023/8/3
     * @updateTime: 14:09  2023/8/3
     ************************************************************************/
    public static void main(String[] args) {
        // 定义一个FunctionTest实例，用于将字符串转换为大写
        FunctionTest<String, String> toUpperCaseFunction = str -> str.toUpperCase();

        // 使用FunctionTest实例处理字符串
        String input = "hello";
        String output = toUpperCaseFunction.apply(input);
        System.out.println(output); // 输出：HELLO

        // 定义一个Function实例，用于将字符串进行反转
        Function<String, String> reverseFunction = str -> new StringBuilder(str).reverse().toString();

        // 使用FunctionTest实例的compose方法，将两个函数组合成一个新的函数
        Function<String, String> composedFunction = toUpperCaseFunction.compose(reverseFunction);

        // 使用组合后的函数处理字符串
        String result = composedFunction.apply(input);
        System.out.println(result); // 输出：OLLEH
    }
}






