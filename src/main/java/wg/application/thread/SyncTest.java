package wg.application.thread;

import org.openjdk.jol.info.ClassLayout;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.atomic.AtomicInteger;

/****************************************************
 * @Package wg.application.thread
 * @author wg
 * @date 2021/2/17 22:02
 * @version
 * @Copyright
 ****************************************************/
@Controller
@RequestMapping
public class SyncTest {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        int i = atomicInteger.incrementAndGet();
        //System.out.println(i);

        Object o = new Object();

        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

    }
}
