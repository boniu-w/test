package wg.application.controller;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/7/29 17:12
 * @version
 * @Copyright
 *************************************************************/
@Controller
@RequestMapping(value = "/aopTestController")
public class AopTestController {

    @RequestMapping(value = "/add")
    @ResponseBody
    public void add(String name,String age) {
        System.out.println("-----------  " + name);
        System.out.println("--------   "+ age);
    }
}
