package wg.application.thread.pool;

import java.util.concurrent.*;

public class ThreadPoolTest2 {
    private static ExecutorService pool;

    public static void main(String[] args) {
        //优先任务队列
        pool = new ThreadPoolExecutor(1,
                2,
                1000,
                TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 20; i++) {
            pool.execute(new ThreadTask2(i));
        }
    }
}

class ThreadTask2 implements Runnable, Comparable<ThreadTask2> {

    private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ThreadTask2() {

    }

    public ThreadTask2(int priority) {
        this.priority = priority;
    }

    //当前对象和其他对象做比较，当前优先级大就返回-1，优先级小就返回1,值越小优先级越高
    public int compareTo(ThreadTask2 o) {
        return this.priority > o.priority ? -1 : 1;
    }

    public void run() {
        try {
            //让线程阻塞，使后续任务进入缓存队列
            Thread.sleep(1000);
            System.out.println("priority:" + this.priority + ",ThreadName:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}