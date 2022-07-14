package wg.application;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateTimeWg {

    public static void main(String[] args) {
        // test();
        contrastDate();
    }

    /************************************************************************
     * @description: 各种转换
     * @author: wg
     * @date: 11:26  2021/9/29
     ************************************************************************/
    public static void test() {
        DateTimeFormatter ftf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter ftf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId zoneId = ZoneId.systemDefault();

        System.out.println("string -> long型的年月日：：" + LocalDate.parse("2018-03-11", ftf1).atStartOfDay(zoneId).toInstant().toEpochMilli());
        System.out.println("String -> LocalDate:  " + LocalDate.parse("2018-03-11", ftf1));
        System.out.println("String -> LocalDateTime:  " + LocalDateTime.parse("2020-07-15 00:00:00", ftf2));

        System.out.println("long -> String型年月日：" + ftf1.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(1594794323956l), zoneId)));
        System.out.println("Long -> Date: " + new Date(1520754566856L));
        System.out.println("Long -> LocalDateTime:  " + LocalDateTime.ofInstant(Instant.ofEpochMilli(1594794323956l), ZoneId.systemDefault()));
        System.out.println("Long -> LocalDate:  " + LocalDateTime.ofInstant(Instant.ofEpochMilli(1594794323956l), ZoneId.systemDefault()).toLocalDate());

        System.out.println("LocalDateTime -> Long:  " + Timestamp.valueOf(LocalDateTime.now()).getTime());
        System.out.println("LocalDateTime -> String:  " + LocalDateTime.now().format(ftf2));

        System.out.println("LocalDate -> LocalDateTime: " + LocalDateTime.of(LocalDate.now(), LocalTime.parse("00:00:00")));
        System.out.println("LocalDate -> Long: " + LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println("LocalDate -> Date: " + Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        System.out.println("LocalDate -> String: " + LocalDate.now().format(ftf1));

        System.out.println("Date -> String:  " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println("Date -> LocalDateTime:  " + LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        System.out.println("Date -> Timestamp:  " + new Timestamp(new Date().getTime()));
        System.out.println("Date -> Long: " + new Date().getTime());
        System.out.println("Date -> Instant:  " + new Date().toInstant());

        System.out.println("long -> Long型的当天开始时间：" + longTodayTimeBeginMs(1594794323956l));
        System.out.println("当天开始时间Long型:" + getTodayTimeBeginMs());
        System.out.println("获取当天结束时间Long型:" + getTodayTimeEndMs());
    }

    //当天的0点时刻
    public static Long getTodayTimeBeginMs() {
        //获取当前日期
        LocalDateTime beginTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        System.out.println("获取当天开始时间：" + beginTime);
        return LocalDateTime.from(beginTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static Long getTodayTimeEndMs() {
        //获取当前日期
        LocalDate nowDate = LocalDate.now();
        //设置零点
        LocalDateTime beginTime = LocalDateTime.of(nowDate, LocalTime.MAX);
        System.out.println("获取当天结束时间:" + beginTime);
        return LocalDateTime.from(beginTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 当天的0点时刻(Long型)
     *
     * @return
     */
    public static Long longTodayTimeBeginMs(Long time) {
        LocalDate longToLocalDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()).toLocalDate();
        LocalDateTime beginTime = LocalDateTime.of(longToLocalDate, LocalTime.MIN);
        return LocalDateTime.from(beginTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /************************************************************************
     * @author: wg
     * @description: 日期加减, 比较
     * @params:
     * @return:
     * @createTime: 14:42  2022/5/9
     * @updateTime: 14:42  2022/5/9
     ************************************************************************/
    public static void contrastDate() {
        //获取当前日期
        LocalDateTime date1 = LocalDateTime.now();

        LocalDateTime date2 = LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(28));

        LocalDateTime date3 = date1.plusHours(-25);

        int i = date1.compareTo(date2);
        System.out.println(i);

        int i1 = date1.compareTo(date3);
        System.out.println(i1);

    }
}
