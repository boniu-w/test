package wg.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author wg
 * @Package wg.application.controller
 * @date 2020/4/15 10:49
 * @Copyright
 */
@Controller
@RequestMapping(value = "/appController")
public class AppController {


    /***************************************************
     * 转驼峰
     * @author: wg
     * @time: 2020/4/21 10:00
     ***************************************************/
    @RequestMapping(value = "/getHumpString{string}")
    @ResponseBody
    public String getHumpString(@RequestParam(value = "string") String string) {

        String[] s = string.split("_");
        StringBuilder stringBuilder = new StringBuilder(s[0]);

        for (int k = 0; k < s.length - 1; k++) {
            stringBuilder.append(s[k + 1].substring(0, 1).toUpperCase() + s[k + 1].substring(1));
        }

        return stringBuilder.toString();
    }


    /***************************************************
     * 转全大写
     * @author: wg
     * @time: 2020/4/21 10:03
     ***************************************************/
    @RequestMapping(value = "/getUppercase{string}", method = RequestMethod.GET)
    @ResponseBody
    public String getUppercase(@RequestParam(value = "string") String string){

        return string.toUpperCase();
    }


}
