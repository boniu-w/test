package wg.application.thread;

/*************************************************************
 * @Package wg.application.thread
 * @author wg
 * @date 2021/2/24 15:11
 * @version
 * @Copyright
 * @discription volatile 测试
 *************************************************************/
public class SyncTest implements Runnable {


    private volatile int count = 100;
    private final Object o = new Object();
    private final Object obj = null;

    public void m() {
        synchronized (obj) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count= " + count);
        }
    }

    public void m0() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count m1= " + count);

    }

    public void m1() {
        synchronized (this) {
            // System.out.println(Thread.currentThread().toString());
            count--;
            System.out.println(Thread.currentThread().getName() + " count m1= " + count);
        }
        try {
            Thread.sleep(1000);
            // this.wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("synchronized 外 count m1= " + count);
    }

    public synchronized void m2() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count m2= " + count);
    }

    public synchronized void m3() {
        count++;
        System.out.println(Thread.currentThread().getName() + " count m3= " + count);
    }

    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count= " + count);
    }


    public static void main(String[] args) {
        SyncTest t = new SyncTest();

        // t.m(); // NullPointerException
        // t.m1();
        // t.m2();
        // System.out.println(t.count);
        //
        for (int i = 0; i < 100; i++) {
            new Thread(t::m2, "线程别名 " + i).start();
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");

        for (int i = 0; i < 100; i++) {
            t.m2();
        }

        System.out.println("-------------------------------------------------");

        for (int i = 0; i < 100; i++) {
            new Thread(t::m2, "线程别名 " + i).start();
        }
        for (int i = 0; i < 100; i++) {
            new Thread(t::m2, "m2 线程别名 " + i).start();
            new Thread(t::m3, "m3 线程别名 " + i).start();
        }
    }
}
