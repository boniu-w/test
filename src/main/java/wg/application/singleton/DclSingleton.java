package wg.application.singleton;

/*************************************************************
 * @Package wg.application.singleton
 * @author wg
 * @date 2021/3/4 15:14
 * @version
 * @Copyright
 * @discription 双重检查 单例
 *************************************************************/
public class DclSingleton {

    private static /*volatile*/ DclSingleton singleton;


    public static DclSingleton getDclSingleton() {

        if (singleton == null) {
            synchronized (DclSingleton.class) {
                if (singleton == null) {
                    singleton = new DclSingleton();
                    return singleton;
                }
            }
        }

        return singleton;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_0000L; i++) {
            DclSingleton instance = DclSingleton.getDclSingleton();
            System.out.print(instance.hashCode() + " ");
        }
    }

}
