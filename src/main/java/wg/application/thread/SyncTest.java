package wg.application.thread;

/*************************************************************
 * @Package wg.application.thread
 * @author wg
 * @date 2021/2/24 15:11
 * @version
 * @Copyright
 * @discription
 *************************************************************/
public class SyncTest implements Runnable {


    private /*volatile*/ int count = 100;
    private Object o = new Object();
    private Object obj = null;

    public void m() {
        synchronized (obj) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count= " + count);
        }
    }

    public void m1() {
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count m1= " + count);
        }
    }

    public synchronized void m2() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count m2= " + count);
    }

    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count= " + count);
    }


    public static void main(String[] args) {
        SyncTest t = new SyncTest();

        //t.m();
        //t.m1();
        //t.m2();
        //System.out.println(t.count);

        for (int i = 0; i < 100; i++) {
            new Thread(t::m2, "t" + i).start();
        }
    }
}
