package wg.application.cas.controller;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

/*************************************************************
 * @Package wg.application.cas.controller
 * @author wg
 * @date 2021/2/25 16:19
 * @version
 * @Copyright
 * @discription
 *************************************************************/
public class AtomicIntegerTest {

    AtomicInteger count = new AtomicInteger(0);
    // IntegerAdder count1 = new IntegerAdder();


    void m() {
        for (int i = 0; i < 10000; i++) {
            count.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        AtomicIntegerTest t = new AtomicIntegerTest();
        ArrayList<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ts.add(new Thread(t::m, "t-" + i));
        }

        ts.forEach((o) -> o.start());

        ts.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);
        System.out.println();
        test2();
    }

    public static void test2() {
        BigDecimal bigDecimal = new BigDecimal("00000000");
        AtomicReference<BigDecimal> safetyFactor = new AtomicReference<>();
        safetyFactor.set(bigDecimal);
        System.out.println(safetyFactor.get());
    }
}
