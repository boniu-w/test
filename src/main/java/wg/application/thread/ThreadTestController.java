package wg.application.thread;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wg.application.vo.Result;

/*************************************************************
 * @Package wg.application.thread
 * @author wg
 * @date 2020/11/25 16:01
 * @version
 * @Copyright
 *************************************************************/
@Controller
@RequestMapping(value = "threadTestController")
public class ThreadTestController {


    @RequestMapping(value = "/print")
    @ResponseBody
    public Result print(){

        print1();
        print2();
        print3();


        return Result.ok();
    }


    public void print1(){
        System.out.println("111111111111111");
        int t=3000;


    }

    public void print2(){
        System.out.println("2222222222222222222");

    }

    public void print3(){

        System.out.println("33333333333333");


    }




}
