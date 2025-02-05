package wg.application.lunar;

import com.nlf.calendar.Fu;
import com.nlf.calendar.Lunar;
import com.nlf.calendar.ShuJiu;
import com.nlf.calendar.Solar;
import com.nlf.calendar.util.LunarUtil;

import java.util.Date;
import java.util.List;

public class LunarTest {

    public static void main(String[] args) {
        getToday();
        getPerson();
        System.out.println();
        getShuJiu();
        getTheDay();
    }

    /**
     * today 年 月 日;
     * 彭祖百忌 等
     */
    public static void getToday() {
        Lunar lunar = Lunar.fromDate(new Date());
        System.out.println("lunar.toFullString() = "+ lunar.toFullString());

        List<String> dayYi = lunar.getDayYi();
        List<String> dayJi = lunar.getDayJi();
        System.out.println("dayYi = " + dayYi);
        System.out.println("dayJi = " + dayJi);
        // Fu fu = lunar.getFu(); // 没入伏 fu=null
        // System.out.println("fu.toFullString() = " + fu.toFullString());
    }

    public static void getTheDay() {
        // Date date = new Date("2024/2/13");
        Date date = new Date("2024/2/14");
        Lunar lunar = Lunar.fromDate(date);
        System.out.println(lunar.toFullString());
    }


    /**
     * 某日是否在数九中, 不在-> 空指针, 在-> 打印
     */
    public static void getShuJiu() {
        // Solar solar = new Solar(2023, 1, 20, 14, 26, 12);
        // Lunar lunar = solar.getLunar();
        Lunar lunar = Lunar.fromDate(new Date());
        // ShuJiu shuJiu = lunar.getShuJiu(); // 没入九 shuJiu=null
        // System.out.println(shuJiu.toFullString());
    }

    public static void getPerson() {
        Solar solar = new Solar(1985, 7, 7, 20, 26, 12);
        Lunar lunar = solar.getLunar();

        System.out.println(lunar.toFullString());
        List<String> dayXiongSha = lunar.getDayXiongSha();
        for (String xs : dayXiongSha) {
            System.out.println("那日凶煞: " + xs);
        }
    }
}
