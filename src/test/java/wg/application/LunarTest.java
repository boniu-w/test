package wg.application;

import com.nlf.calendar.Holiday;
import com.nlf.calendar.JieQi;
import com.nlf.calendar.Lunar;
import com.nlf.calendar.Solar;
import com.nlf.calendar.util.HolidayUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class LunarTest {

    @Test
    public void holidayTest() {
        List<Holiday> holidays = HolidayUtil.getHolidays(2024);
        System.out.println(holidays);

        List<Holiday> list = HolidayUtil.getHolidays(2024, 4);
        System.out.println(list);
    }

    @Test
    public void jieqiTest() {
        Lunar lunar = new Lunar();

        List<String> dayJiShen = lunar.getDayJi();
        System.out.println("忌: " + dayJiShen);

        List<String> dayYi = lunar.getDayYi();
        System.out.println("宜: " + dayYi);
    }
}
