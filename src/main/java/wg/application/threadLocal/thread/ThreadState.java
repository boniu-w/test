package wg.application.threadLocal.thread;

/*************************************************************
 * @Package wg.application.threadLocal.thread
 * @author wg
 * @date 2021/2/24 14:48
 * @version
 * @Copyright
 * @discription
 *************************************************************/
public class ThreadState {


    static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println("--  " + this.getState());

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();

        System.out.println(myThread.getState());
        myThread.start();

        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(myThread.getState());
    }
}
