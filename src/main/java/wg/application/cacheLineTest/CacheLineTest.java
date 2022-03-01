package wg.application.cacheLineTest;


/*************************************************************
 * @Package wg.application.CacheLineTest
 * @author wg
 * @date 2021/2/19 10:35
 * @version
 * @Copyright
 *************************************************************/
public class CacheLineTest {
    private static class T {
        public volatile long x = 0L;
    }

    public static T[] arr = new T[2];

    static {
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (long i = 0; i < 1000_0000L; i++) {
                arr[0].x = i;
            }
        });

        Thread t2 = new Thread(() -> {
            for (long i = 0; i < 1000_0000L; i++) {
                arr[0].x = i;
            }
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println((System.nanoTime() - start) / 100_0000);

    }


}
