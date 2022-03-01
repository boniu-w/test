package wg.application.enumeration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.enumeration.EnumTest;
import wg.application.vo.Result;

/*************************************************************
 * @Package wg.application.enumeration.controller
 * @author wg
 * @date 2020/8/24 10:17
 * @version
 * @Copyright
 *************************************************************/
@RestController
@RequestMapping(value = "/enumTestController")
public class EnumTestController {

    @RequestMapping(value = "/test1")
    public Result test1() {
        int maxInt = EnumTest.MAX_INT.getMaxInt();
        System.out.println(maxInt);

        try {
            EnumTest.MAX_INT.sleep(3000);
            System.out.println("-------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }
}
