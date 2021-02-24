package wg.application.thread;

/*************************************************************
 * @Package wg.application.thread
 * @author wg
 * @date 2021/2/24 15:44
 * @version
 * @Copyright
 * @discription 测试结果显示实际是两个线程
 *************************************************************/
public class ThreadTest {

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + " m1 start...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("m1 end ...");

    }

    public void m2() {
        System.out.println(Thread.currentThread().getName() + " m2 start");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("m2 end ... ");
    }

    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();

        new Thread(threadTest::m1,"t1").start();
        new Thread(threadTest::m2,"t2").start();

    }
}
