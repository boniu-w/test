package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.date.DateTestWg;
import wg.application.util.DateUtil;
import wg.application.util.StringUtil;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
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

        System.out.println(decimal); // 2.5
        System.out.println(i); // 912
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

    @Test
    public void testToString() {
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();

        System.out.println("date = " + date.toString()); // Thu Oct 12 15:16:57 CST 2023
        System.out.println("localDateTime = " + localDateTime.toString()); // 2023-10-12T15:16:57.759
        System.out.println("localDate = " + localDate.toString()); // 2023-10-12

        boolean b = DateUtil.isDate(date.toString());
        boolean b1 = DateUtil.isDate(localDateTime.toString());
        boolean b2 = DateUtil.isDate(localDate.toString());

        System.out.println(b); // true
        System.out.println("b1 = " + b1); // true
        System.out.println("b2 = " + b2); // true
    }

    @Test
    public void testIsDateTime() {
        String a = "2022-05-01";
        boolean b = DateUtil.isValidDateTime(a);
        boolean b1 = DateUtil.isValidDate(a);

        System.out.println("b = " + b); // false
        System.out.println("b1 = " + b1); // true

        LocalDate localDate = DateUtil.toLocalDate(a, "yyyy-MM-dd");
        System.out.println("localDate = " + localDate); // 2022-05-01
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(localDate);
        System.out.println("localDateTime = " + localDateTime); // 2022-05-01T00:00

        LocalDateTime now = LocalDateTime.now();

        Duration between = Duration.between(localDateTime, now);
        System.out.println("between.toDays() = " + between.toDays()); // 561

        Duration between2 = Duration.between(now, localDateTime);
        System.out.println("between2.toDays() = " + between2.toDays()); // -561

        LocalDateTime now1 = LocalDateTime.now();
        Duration between3 = Duration.between(now, now1);
        System.out.println("between3.toDays() = " + between3.toDays()); // 0
    }

    /**
     * @author: wg
     * @description: Date 构造函数中的年份是从 1900 年开始计算的，所以 123 表示 2023 年，124 表示 2024 年。月份从 0 开始，所以 4 表示 5月。
     * @params:
     * @return:
     * @createTime: 17:39  2024/5/16
     * @updateTime: 17:39  2024/5/16
     */
    @Test
    public void testSub() {
        Date date = new Date(123, 6, 1);

        // 计算相差的年数
        LocalDate commissioningDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        // 获取当前时间的LocalDateTime
        LocalDateTime now = LocalDateTime.now();
        // Period between = Period.between(commissioningDateTime, now);

        long yearsDifference = ChronoUnit.YEARS.between(commissioningDateTime, now);
        System.out.println("yearsDifference = " + yearsDifference);

        Date date1 = new Date(123, 4, 1);
        LocalDateTime commissioningDateTime1 = date1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        // 获取当前时间的LocalDateTime
        now = LocalDateTime.now();
        yearsDifference = ChronoUnit.YEARS.between(commissioningDateTime1, now);
        System.out.println("yearsDifference = " + yearsDifference);

        String dateStr = "2023/06/01";
        LocalDate localDate = DateUtil.toLocalDate(dateStr, "yyyy/MM/dd");
        long between = ChronoUnit.YEARS.between(localDate, now);
        System.out.println("between = " + between);
    }
}
