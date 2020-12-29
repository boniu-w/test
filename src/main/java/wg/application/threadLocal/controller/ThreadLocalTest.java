package wg.application.threadLocal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import wg.application.threadLocal.thread.Thread1;
import wg.application.threadLocal.thread.Thread2;

import java.util.concurrent.locks.LockSupport;

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


    private static Thread t1;
    private static Thread t2;

    @RequestMapping(value = "/test1")
    public void test1() {

        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        System.out.println(threadLocal.toString());

    }

    public static void main(String[] args) {
        char[] a1 = "123456789".toCharArray();
        char[] a2 = "abcdefghi".toCharArray();


        t1 = new Thread(() -> {
            for (char num : a1) {
                System.out.print(num);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        }, "t1");

        t2 = new Thread(() -> {
            for (char c : a2) {
                LockSupport.park();
                System.out.print(c);
                LockSupport.unpark(t1);
            }
        }, "t2");

        t1.start();
        t2.start();
    }


    @Autowired
    private Thread1 thread1;

    @Autowired
    private Thread2 thread2;

    @RequestMapping(value = "/threadTestAlternate")
    @ResponseBody
    public void threadTestAlternate() {
        thread1.start();
        thread2.start();
    }


}
