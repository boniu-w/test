package wg.application.lunar;

import com.nlf.calendar.Lunar;
import com.nlf.calendar.ShuJiu;
import com.nlf.calendar.Solar;

import java.util.Date;

public class LunarTest {

    public static void main(String[] args) {
        getToday();
        System.out.println();
        getShuJiu();
    }

    /**
     * today 年 月 日;
     * 彭祖百忌 等
     */
    public static void getToday() {
        Lunar lunar = Lunar.fromDate(new Date());
        System.out.println(lunar.toFullString());
    }

    /**
     某日是否在数九中, 不在-> 空指针, 在-> 打印 */
    public static void getShuJiu() {
        Solar solar = new Solar(2021, 12, 21, 14, 26, 12);
        Lunar lunar = solar.getLunar();
        ShuJiu shuJiu = lunar.getShuJiu();
        System.out.println(shuJiu.toFullString());
    }
}
