package wg.application.beichengamble.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wg.application.beichengamble.mapper.GambleMapper;

import java.util.List;

/**
 * @author wg
 * @Package wg.application.beichengamble.controller
 * @date 2020/5/9 15:23
 * @Copyright
 */
@Controller
@RequestMapping(value = "/gambleController")
public class GambleController {


    @Autowired
    public GambleMapper gambleMapper;


    @RequestMapping(value = "/test1")
    @ResponseBody
    public List test1() {
        System.out.println("-----------");
        List list = gambleMapper.selectForCardNo();
        List list1 = gambleMapper.selectForDuiShouCardNo();

        System.out.println(list.size());

        return list;


    }


}
