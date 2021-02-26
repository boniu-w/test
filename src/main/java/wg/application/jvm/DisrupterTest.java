package wg.application.jvm;

/*************************************************************
 * @Package wg.application.jvm
 * @author wg
 * @date 2021/2/19 11:20
 * @version
 * @Copyright
 * @discription cpu的乱序执行, 指令重排序验证
 *************************************************************/
public class DisrupterTest {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;

            Thread one = new Thread(() -> {
                a = 1;
                x = b;
            });

            Thread other = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });

            one.start();
            other.start();
            try {
                one.join();
                other.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String message = "第 " + i + "次的结果(" + x + ", " + y + ")";
            if (x == 0 && y == 0) {
                System.out.println(message);
                break;
            }

        }

    }


}
