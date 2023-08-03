package wg.application.functionalinterface;

import java.util.Objects;
import java.util.function.Function;

/************************************************************************
 * @author: wg
 * @description: 一个参数, 一个返回值
 * @createTime: 10:48 2022/6/9
 * @updateTime: 10:48 2022/6/9
 ************************************************************************/
@FunctionalInterface
public interface FunctionTest<T, R> {

    R apply(T t);

    default <V> Function<V,R> compose(Function<? super V, ? extends T> before){
        Objects.requireNonNull(before);
        return (V v)-> apply(before.apply(v));
    }
}
