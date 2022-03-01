package wg.application.cacheLineTest;

/*************************************************************
 * @Package wg.application.cacheLineTest
 * @author wg
 * @date 2021/2/19 10:48
 * @version
 * @Copyright
 * @discription 缓存行对齐
 *************************************************************/
public class CacheLineTest2 {

    private static class Padding {
        public volatile long p1, p2, p3, p4, p5, p6, p7;
    }

    private static class T extends Padding {
        public volatile long x = 0L;
    }

    public static T[] arr = new T[2];

    static {
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (long i = 0; i < 10_000_000L; i++) {
                arr[0].x = i;
            }
        });

        Thread t2 = new Thread(() -> {
            for (long i = 0; i < 10_000_000L; i++) {
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

        System.out.println((System.nanoTime() - start) / 1_000_000);

    }
}
