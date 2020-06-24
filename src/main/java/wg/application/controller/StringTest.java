package wg.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.StandardCharsets;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/6/24 13:47
 * @version
 * @Copyright
 *************************************************************/
@Controller
@RequestMapping(value = "/stringTest")
public class StringTest {



    @RequestMapping(value = "/getBytesTest")
    @ResponseBody
    public void getBytesTest(){
        String s="王刚";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }

    }
}
