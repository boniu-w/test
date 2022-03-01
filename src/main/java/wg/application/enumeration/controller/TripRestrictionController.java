package wg.application.enumeration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.enumeration.TripRestriction;
import wg.application.vo.Result;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;

/*************************************************************
 * @Package net.mingsoft.cms.action
 * @author wg
 * @date 2020/9/11 16:45
 * @version
 * @Copyright
 *************************************************************/
@RestController
@RequestMapping(value = "/tripRestrictionController")
public class TripRestrictionController {

    @RequestMapping(value = "/getTripRestriction")
    public Result getTripRestriction() {
        String[] tripRestriction= new String[]{};

        LocalDateTime now = LocalDateTime.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        System.out.println(dayOfWeek.getValue());

        TripRestriction[] values = TripRestriction.values();
        System.out.println(Arrays.toString(values));

        for (int i = 0; i < values.length; i++) {

            // 获取 今天是周几-限行号
            if (dayOfWeek.getValue()== values[i].getCode()){
                 tripRestriction = values[i].getTripRestriction();

            }
        }

        return Result.ok(tripRestriction);
    }
}
