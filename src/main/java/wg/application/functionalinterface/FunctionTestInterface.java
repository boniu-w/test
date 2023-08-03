package wg.application.functionalinterface;

import java.util.function.Function;

/************************************************************************
 * author: wg
 * description: FunctionTestInterface 
 * createTime: 14:16 2023/8/3
 * updateTime: 14:16 2023/8/3
 ************************************************************************/
public interface FunctionTestInterface {

    <T, R> R test(Function<T, R> function, T input);

    <R> R test(Function<String, R> function, String input);
}
