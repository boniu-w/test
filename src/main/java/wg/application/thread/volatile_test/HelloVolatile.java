package wg.application.thread.volatile_test;

import java.util.concurrent.TimeUnit;

/*************************************************************
 * @Package wg.application.thread.volatile_test
 * @author wg
 * @date 2021/2/19 10:00
 * @version
 * @Copyright
 *************************************************************/
public class HelloVolatile {

    volatile boolean running = true;
    //static boolean running = true;

    void m() {
        System.out.println("m start");
        while (running) {
            //System.out.println("m is running");
        }
        System.out.println("m end");
    }


    public static void main(String[] args) {
        HelloVolatile t = new HelloVolatile();
        new Thread(t::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.running = false;
    }

}
