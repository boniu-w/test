package wg.application.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
    public void getBytesTest() {
        String s = "王刚";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);

        System.out.println(Arrays.toString(bytes));

    }

    /*****************************************************
    * @params:
    * @description: (String)、toString、String.valueOf 的区别
    * @author: wg
    * @date: 2021/8/12 11:46
    *****************************************************/
    public static void main(String[] args) {
        Object s = null;

        System.out.println(String.valueOf(s) == null); // false
        System.out.println(String.valueOf(s).equals("null")); // true

        if (StringUtils.isBlank(String.valueOf(s))){
            System.out.println("s is blank");
        }

        String s1 = (String) s; // 不报异常
        s.toString(); // 报异常 空指针

        Object a = new Integer(1);
        String as =(String) a; // 报异常 ClassCastException  .Integer cannot be cast to java.lang.String
    }
}
