package wg.application.threadLocal.controller;

/*************************************************************
 * @Package wg.application.threadLocal.controller
 * @author wg
 * @date 2021/2/22 15:32
 * @version
 * @Copyright
 * @discription 软引用, 多用在缓存技术中
 *************************************************************/
public class SoftReference {


    public static void main(String[] args) {
        java.lang.ref.SoftReference<byte[]> softReference = new java.lang.ref.SoftReference<>(new byte[1024 * 1024 * 20]);

        System.out.println(softReference.get());
        System.gc();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(softReference.get());

        byte[] b = new byte[1024 * 1024 * 35];
        System.out.println(softReference.get());

    }
}
