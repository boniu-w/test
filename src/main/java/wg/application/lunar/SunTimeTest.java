package wg.application.lunar;

import org.shredzone.commons.suncalc.SunTimes;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class SunTimeTest {

    public static void main(String[] args) {
        SunTimeTest sunTimeTest = new SunTimeTest();
        sunTimeTest.test();
    }

    // lat 纬度
    // lng 经度
    public void test() {
        ZonedDateTime beijingTime = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        ZonedDateTime newyorkTime = beijingTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime sydneyTime = beijingTime.withZoneSameInstant(ZoneId.of("Australia/Sydney")); // 悉尼

        // double lat = 39.90277565497976, lng = 116.40114474433395;          // 北京
        // double lat = 40.72200810582801, lng = -74.0124769513162;          // 纽约
        double lat = -33.86948089803011, lng = 151.21591160762975;          // 悉尼

        SunTimes times = SunTimes.compute()
                .on(sydneyTime)
                .at(lat, lng)
                .execute();
        System.out.println("Sunrise: " + times.getRise());
        System.out.println("Sunset: " + times.getSet());
        System.out.println("times.getNoon() = " + times.getNoon());
        System.out.println("times.getNadir() = " + times.getNadir());
    }
}
