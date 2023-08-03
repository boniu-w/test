package wg.application.functionalinterface;

/************************************************************************
 * @author: wg
 * @description: 不接收参数, 有返回值
 * @params:
 * @return:
 * @createTime: 10:46  2022/6/9
 * @updateTime: 10:46  2022/6/9
 ************************************************************************/
@FunctionalInterface
public interface SupplierTest<T> {

    T get();
}
