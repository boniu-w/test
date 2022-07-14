package wg.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wg.application.config.PropertiesTest;

/********************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/6/16 9:32
 * @version
 * @Copyright
 ********************************************************/
@Controller
@RequestMapping(value = "/configTest")
// @Configuration
public class ConfigTest {

    @Autowired
    PropertiesTest propertiesTest;

    @Value(value = "${wg.name}")
    String name;

    /*************************************************************
     * 自己实例化PropertiesTest 和 由spring 生成bean 有巨大差别 下面的 new
     * 是得不到name的;
     * @author: wg
     * @time: 2020/6/16 9:53
     *************************************************************/
    @RequestMapping(value = "/getName")
    @ResponseBody
    public String getName() {
        System.out.println(name);

        //PropertiesTest propertiesTest = new PropertiesTest();
        name = propertiesTest.getName();


        return name;
    }
}
