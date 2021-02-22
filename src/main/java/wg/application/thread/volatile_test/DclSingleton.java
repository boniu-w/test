package wg.application.thread.volatile_test;

/*************************************************************
 * @Package wg.application.thread.volatile_test
 * @author wg
 * @date 2021/2/22 15:01
 * @version
 * @Copyright
 * @discription
 *************************************************************/
public class DclSingleton {

    private static volatile DclSingleton INSTANCE;

    private DclSingleton() {

    }

    public static DclSingleton getInstance() {

        if (INSTANCE == null) {
            synchronized (DclSingleton.class) {
                if (INSTANCE == null) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    INSTANCE = new DclSingleton();
                }
            }
        }


        return INSTANCE;
    }

    private void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_0000L; i++) {
            DclSingleton instance = DclSingleton.getInstance();
            System.out.println(instance.hashCode());
        }
    }

}
