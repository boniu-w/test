package wg.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wg.application.entity.BankFlow;
import wg.application.vo.Result;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/8/4 14:18
 * @version
 * @Copyright
 *************************************************************/
@RequestMapping(value = "/fieldTest")
@Controller
public class FieldTest {

    @ResponseBody
    @RequestMapping(value = "/getFieldAnything")
    public Result getFieldAnything(){
        List<String> names= new ArrayList<>();

        BankFlow bankFlow = new BankFlow();
        Field[] declaredFields = bankFlow.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            String name = declaredFields[i].getName();
            System.out.println(name);
            names.add(name);
        }


        return Result.ok(names);
    }
}
