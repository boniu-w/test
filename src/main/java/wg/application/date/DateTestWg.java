package wg.application.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 9:44 2022/6/17
 * @updateTime: 9:44 2022/6/17
 ************************************************************************/
public class DateTestWg {

    public final static String DATE_PATTERN = "yyyy-MM-dd";
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String ZONE_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm ZZZZ";
    public final static String ZH_PATTERN = "yyyy MMM dd EE HH:mm";
    public final static String US_PATTERN = "E, MMMM/dd/yyyy HH:mm";

    public void test1() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(US_PATTERN, Locale.US);
        String format = dateTimeFormatter.format(zonedDateTime);
        System.out.println(format);

        LocalDateTime localDateTime = LocalDateTime.now();
        String format1 = DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
        System.out.println(format1);

        String format2 = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime);
        System.out.println(format2);
    }
}
