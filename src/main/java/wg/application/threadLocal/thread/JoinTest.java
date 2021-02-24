package wg.application.threadLocal.thread;

/*************************************************************
 * @Package wg.application.threadLocal.thread
 * @author wg
 * @date 2021/2/24 14:37
 * @version
 * @Copyright
 * @discription
 *************************************************************/
public class JoinTest {

    public static void main(String[] args) {
        testJoin();
    }

    static void testJoin(){
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("t1 " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 10; i++) {
                System.out.println("t2 "+i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        t1.start();
        t2.start();
    }


}
