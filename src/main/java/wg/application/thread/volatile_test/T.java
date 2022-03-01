package wg.application.thread.volatile_test;

import java.util.ArrayList;

/*************************************************************
 * @Package wg.application.thread.threadpoll
 * @author wg
 * @date 2021/2/25 15:57
 * @version
 * @Copyright
 * @discription
 *************************************************************/
public class T {

    volatile int count = 0;

    /*synchronized*/ void m() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        T t = new T();

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

    }


}
