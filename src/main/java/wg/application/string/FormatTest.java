package wg.application.string;

import java.time.LocalDateTime;
import java.util.Date;

public class FormatTest {

    public static void main(String[] args) {
        test1();
        test02();
    }

    /************************************************************************
     * @description: 左对齐
     * @author: wg
     * @date: 10:15  2021/11/26
     * @params:
     * @return:
     ************************************************************************/
    public static void test1() {
        for (int i = 0; i < 10; i++) {
            System.out.print(String.format("%-10s", 123123123));
        }
    }

    public static void test02() {
        LocalDateTime now = LocalDateTime.now();
        String da = now.toString();
        System.out.println(da);
        String format = String.format("{0:yyyy:MM:dd}", now);
        System.out.println(format);

        String a = "20071027";
        // System.out.println(String.format("%tc%n", now));

        Date date = new Date();
        System.out.println(String.format("%tc", date));
        System.out.println(String.format("%tF", date));
        System.out.println(String.format("%tD", date));
        System.out.println(String.format("%tR", date)); // 没有秒
        System.out.println(String.format("%tr", date));
        System.out.println(String.format("%tT", date));
    }
}
