package wg.application.threadLocal.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.LockSupport;

/*************************************************************
 * @Package wg.application.threadLocal.thread
 * @author wg
 * @date 2020/12/28 10:42
 * @version
 * @Copyright
 *************************************************************/
@Component
public class Thread1 extends Thread{
    private char[] a1 = "123456789".toCharArray();

    @Autowired
    private Thread2 thread2;

    @Override
    public void run() {
        for (char num : a1) {
            System.out.print(num);
            LockSupport.unpark(thread2);
            LockSupport.park();
        }
    }
}
