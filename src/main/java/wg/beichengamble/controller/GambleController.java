package wg.beichengamble.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wg.beichengamble.mapper.GambleMapper;

import java.util.List;

/**
 * @author wg
 * @Package wg.beichengamble.controller
 * @date 2020/5/9 15:23
 * @Copyright
 */
@Controller
@RequestMapping(value = "/gambleController")
public class GambleController {


    @Autowired
    GambleMapper gambleMapper;


    public void test1(){
        List list = gambleMapper.selectForCardNo();
        List list1 = gambleMapper.selectForDuiShouCardNo();




    }


}
