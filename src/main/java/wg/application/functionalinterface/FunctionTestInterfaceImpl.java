package wg.application.functionalinterface;

import java.util.function.Function;

/************************************************************************
 * author: wg
 * description: FunctionTestInterfaceImpl 
 * createTime: 14:18 2023/8/3
 * updateTime: 14:18 2023/8/3
 ************************************************************************/
public class FunctionTestInterfaceImpl implements FunctionTestInterface {
    @Override
    public <T, R> R test(Function<T, R> function, T input) {
        R result = function.apply(input);

        return result;
    }

    public <R> R test(Function<String, R> function, String input) {
        R result = function.apply(input);

        return result;
    }

    public static void main(String[] args) {
        FunctionTestInterfaceImpl anInterface = new FunctionTestInterfaceImpl();
        String hello = anInterface.test(String::toUpperCase, "hello");
        System.out.println("hello = " + hello); // HELLO

        Integer test = anInterface.test(e -> e.compareTo("a"), "hello");
        System.out.println("test = " + test); // 7
    }
}
