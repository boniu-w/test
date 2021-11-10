package wg.application;

import org.junit.Test;

import java.math.BigDecimal;

public class MathTest {

    @Test
    public void test(){

        // scale 小数点后的位数
        double d = 2.22D;
        BigDecimal dd = new BigDecimal(d);
        int scale = dd.scale();
        System.out.println(dd); // 2.220000000000000195399252334027551114559173583984375
        System.out.println(scale); // 51

        double i = -2.22D;
        BigDecimal ii = new BigDecimal(i);
        System.out.println(ii);  // -2.220000000000000195399252334027551114559173583984375
        System.out.println(ii.scale()); // 51

        BigDecimal ss = new BigDecimal("2.22");
        System.out.println(ss.scale());

        // signum 正负号
        System.out.println(dd.signum()); // 1
        System.out.println(ii.signum()); // -1
    }
}
