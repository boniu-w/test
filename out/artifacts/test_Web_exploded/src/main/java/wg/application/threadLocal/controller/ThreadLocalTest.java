package wg.application.threadLocal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*************************************************************
 * @Package wg.application.threadLocal.controller
 * @author wg
 * @date 2020/8/25 15:06
 * @version
 * @Copyright
 *************************************************************/
@RestController
@RequestMapping(value = "/threadLocalController")
public class ThreadLocalTest {


    @RequestMapping(value = "/test1")
    public void test1(){

        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        System.out.println(threadLocal.toString());


    }
}
