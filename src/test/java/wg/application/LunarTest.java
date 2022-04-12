package wg.application;

import com.nlf.calendar.Holiday;
import com.nlf.calendar.util.HolidayUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class LunarTest {

    @Test
    public void holidayTest() {
        List<Holiday> holidays = HolidayUtil.getHolidays(2020);
        System.out.println(holidays);

        List<Holiday> list = HolidayUtil.getHolidays(2022, 2);
        System.out.println(list);
    }
}
