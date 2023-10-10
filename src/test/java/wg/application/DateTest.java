package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import wg.application.date.DateTestWg;
import wg.application.util.DateUtil;
import wg.application.util.StringUtil;

import java.time.Instant;
import java.time.LocalDate;
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
    public void test() {
        Date date = new Date();
        BigDecimal decimal = new BigDecimal("2.5");
        int i = decimal.multiply(new BigDecimal("365")).intValue();

        System.out.println(decimal);
        System.out.println(i);
    }

    @Test
    public void test2() {
        DateTestWg dateTestWg = new DateTestWg();
        dateTestWg.test1();
    }

    @Test
    public void testFrom() {
        Date date = new Date();

        Instant now = Instant.now();

        System.out.println(now.toEpochMilli());
        System.out.println(now.toString());
        System.out.println(Date.from(now));

    }

    @Test
    public void testFormat() {
        String a = "2024/05/01";
        String pattern = "yyyy/MM/dd";
        LocalDate localDate = DateUtil.toLocalDate(a, pattern);
        System.out.println(localDate);

        String standardLocalDatetimeStr = StringUtil.toStandardLocalDateStr(a);
        System.out.println("standardLocalDatetimeStr = " + standardLocalDatetimeStr);
    }
}
