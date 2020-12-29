package wg.application.threadLocal.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.LockSupport;

/*************************************************************
 * @Package wg.application.threadLocal.thread
 * @author wg
 * @date 2020/12/28 10:43
 * @version
 * @Copyright
 *************************************************************/
@Component
public class Thread2 extends Thread {
    private char[] a2 = "abcdefghi".toCharArray();

    @Autowired
    private Thread1 thread1;

    @Override
    public void run() {
        for (char c : a2) {
            LockSupport.park();
            System.out.print(c);
            LockSupport.unpark(thread1);
        }
    }
}
