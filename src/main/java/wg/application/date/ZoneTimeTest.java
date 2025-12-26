package wg.application.date;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZoneTimeTest {

    public static void main(String[] args) {
        ZoneTimeTest zoneTimeTest = new ZoneTimeTest();
        zoneTimeTest.test();
    }
    public void test() {
        ZonedDateTime beijingTime = ZonedDateTime.now(ZoneOffset.ofHours(8));
        // ZonedDateTime beijingTime = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));  // 两种写法都可以
        ZonedDateTime newYorkTime = beijingTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime sydneyTime = beijingTime.withZoneSameInstant(ZoneId.of("Australia/Sydney")); // 悉尼

        System.out.println("beijingTime = " + beijingTime);
        System.out.println("newYorkTime = " + newYorkTime);
        System.out.println("sydneyTime = " + sydneyTime);
    }
}
