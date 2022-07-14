package wg.application.interfaces.functionalinterface;

/************************************************************************
 * @author: wg
 * @description: 有参数, 没有返回值
 * @params:
 * @return:
 * @createTime: 10:46  2022/6/9
 * @updateTime: 10:46  2022/6/9
 ************************************************************************/
@FunctionalInterface
public interface ConsumerTest<T> {

    void accept(T t);
}
