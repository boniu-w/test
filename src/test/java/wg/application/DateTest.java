package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.DateUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 15:19 2022/5/13
 * @updateTime: 15:19 2022/5/13
 ************************************************************************/
@SpringBootTest
public class DateTest {

    @Test
    public void test(){
        Date date = new Date();
        BigDecimal decimal = new BigDecimal("2.5");
        int i = decimal.multiply(new BigDecimal("365")).intValue();
        Date date1 = DateUtils.addDateDays(date, i);

        System.out.println(decimal);
        System.out.println(i);
        System.out.println(date1);
    }
}
