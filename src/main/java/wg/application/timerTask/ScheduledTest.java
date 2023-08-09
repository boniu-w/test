package wg.application.timerTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledTest {


    public void test01() throws InterruptedException {
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(20);
        System.out.println("start: " + System.currentTimeMillis());
        // for (; ; ) {
        // scheduled.scheduleWithFixedDelay(() -> this.print(), 2, 300, TimeUnit.MILLISECONDS);
        // System.out.println("end:   " + System.currentTimeMillis());

        // Thread.sleep(5000);
        // }

        scheduled.scheduleAtFixedRate(() -> this.print(), 2, 300, TimeUnit.MILLISECONDS);
        System.out.println("end:   " + System.currentTimeMillis());
    }

    public void print() {
        System.out.println("--------");
    }

    public void test02() throws InterruptedException {
        // 创建大小为5的线程池
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 3; i++) {
            Task worker = new Task("task-" + i);
            // 只执行一次
            // scheduledThreadPool.schedule(worker, 5, TimeUnit.SECONDS);
            // 周期性执行，每5秒执行一次
            scheduledThreadPool.scheduleAtFixedRate(worker, 0, 5, TimeUnit.SECONDS);
        }

        Thread.sleep(10000);

        System.out.println("Shutting down executor...");
        // 关闭线程池
        scheduledThreadPool.shutdown();
        boolean isDone;
        // 等待线程池终止
        do {
            isDone = scheduledThreadPool.awaitTermination(1, TimeUnit.DAYS);
            System.out.println("awaitTermination...");
        } while (!isDone);

        System.out.println("Finished all threads");
    }

    class Task implements Runnable {

        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("name = " + name + ", startTime = " + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("name = " + name + ", endTime   = " + System.currentTimeMillis());
        }
    }
}