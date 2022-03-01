package wg.application.thread.volatile_test;

/*************************************************************
 * @Package wg.application.thread.volatile_test
 * @author wg
 * @date 2021/2/26 10:04
 * @version
 * @Copyright
 * @discription
 *************************************************************/
public class VolatileReadWrite {

    private int a = 0;
    private /*volatile*/ boolean flag = false;

    public void write() {
        a = 1;
        flag = true;
    }

    public void read() {
        if (flag) {
            int i = a;
            System.out.println(i);
        }

        System.out.println(flag);
    }

    public static void main(String[] args) {
        VolatileReadWrite volatileReadWrite = new VolatileReadWrite();

        new Thread(volatileReadWrite::write, "thread write").start();

        new Thread(volatileReadWrite::read, "thread read").start();



    }

}
