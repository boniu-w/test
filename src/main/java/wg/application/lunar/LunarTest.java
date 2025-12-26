package wg.application.lunar;

import com.nlf.calendar.*;
import com.nlf.calendar.util.LunarUtil;

import java.util.Date;
import java.util.List;

public class LunarTest {

    public static void main(String[] args) {
        getToday();
        getPerson();
        System.out.println();

        getFu();
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
        // Date date = new Date("1953/6/14");
        // Lunar lunar = Lunar.fromDate(date);
        // System.out.println(lunar.toFullString());
    }


    /**
     * 某日是否在数九中, 不在-> 空指针, 在-> 打印
     */
    public static void getShuJiu() {
        // Solar solar = new Solar(2023, 1, 20, 14, 26, 12);
        // Lunar lunar = solar.getLunar();
        Lunar lunar = Lunar.fromDate(new Date());
        ShuJiu shuJiu = lunar.getShuJiu(); // 没入九 shuJiu=null
        System.out.println(shuJiu.toFullString());
    }

    public static void getFu(){
        Lunar lunar = Lunar.fromDate(new Date());
        Fu fu = lunar.getFu();
        System.out.println("fu = " + fu);
    }

    public static void getPerson() {
        // Lunar lunar = new Lunar(1985, 5, 20, 19, 38, 12);
        Solar solar = new Solar(1985, 7, 7, 20, 1, 12);
        // Solar solar = new Solar(1984, 7, 7, 20, 26, 12);
        // Solar solar = new Solar(2022, 3, 13, 13, 38, 12); // 王一迪
        // Solar solar = new Solar(2016, 3, 25, 13, 38, 12); // 王一凡

        Lunar lunar = solar.getLunar();

        System.out.println(lunar.toFullString());
        List<String> dayXiongSha = lunar.getDayXiongSha();
        for (String xs : dayXiongSha) {
            System.out.println("那日凶煞: " + xs);
        }

        EightChar eightChar = lunar.getEightChar();
        System.out.println("eightChar = " + eightChar);

        List<String> baZiWuXing = lunar.getBaZiWuXing();
        System.out.println("baZiWuXing = " + baZiWuXing);

        List<String> baZiShiShenGan = lunar.getBaZiShiShenGan();
        System.out.println("baZiShiShenGan = " + baZiShiShenGan);

        List<String> baZiShiShenZhi = lunar.getBaZiShiShenZhi();
        System.out.println("baZiShiShenZhi = " + baZiShiShenZhi);
    }
}
