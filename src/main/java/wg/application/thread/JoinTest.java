package wg.application.thread;

/************************************************************************
 * @author: wg
 * @description: join方法的主要作用就是同步，它可以使得线程之间的并行执行变为串行执行
 * @params:
 * @return:
 * @createTime: 15:19  2022/1/21
 * @updateTime: 15:19  2022/1/21
 ************************************************************************/
public class JoinTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadJoinTest t1 = new ThreadJoinTest("小");
        ThreadJoinTest t2 = new ThreadJoinTest("中");
        ThreadJoinTest t3 = new ThreadJoinTest("大");
        t1.start();
        /**join的意思是使得放弃当前线程的执行，并返回对应的线程，例如下面代码的意思就是：
         程序在main线程中调用t1线程的join方法，则main线程放弃cpu控制权，并返回t1线程继续执行直到线程t1执行完毕
         所以结果是t1线程执行完后，才到主线程执行，相当于在main线程中同步t1线程，t1执行完了，main线程才有执行的机会
         */
        // t1.join();
        // t1.join(10); // 程序执行前面10毫秒内打印的都是小明线程，10毫秒后，小明和小东程序交替打印。
        t3.start();
        t1.join();
        t2.start();
        // t1.join();
        // t2.join();
    }

}

class ThreadJoinTest extends Thread {
    public ThreadJoinTest(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(this.getName() + ":" + i);
        }
    }
}
