package wg.application.math;

public class Test01 {

    /************************************************************************
     * @description: 浮点数精度
     * @author: wg
     * @date: 16:19  2021/12/23
     * @params:
     * @return:
     ************************************************************************/
    public void test01() {
        float aFloat = 2.07F * 100;
        double aDouble = 1D;
        double sum = aFloat - aDouble;
        System.out.println("sum: " + sum);
    }
}
