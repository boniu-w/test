package wg.application.math;

import java.math.BigDecimal;

public class FloatTest {

    /************************************************************************
     * @description: 浮点数计算精度
     * 源码 反码 补码
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

    public void test02() {
        double a = 0.1D;
        BigDecimal ade = new BigDecimal(a);
        System.out.println(ade);

        BigDecimal aval = BigDecimal.valueOf(a);
        System.out.println(aval);
    }

    /************************************************************************
     * @description: float 转 二进制 字符串
     * @author: wg
     * @date: 17:20  2021/12/24
     * @params:
     * @return:
     ************************************************************************/
    public void float2Binary() {
        double maxValue = Double.MAX_VALUE;
        double minValue = -Double.MIN_VALUE;
        System.out.println(maxValue);
        System.out.println(minValue);
    }

}
