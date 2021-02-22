package wg.application.threadLocal.controller;

/*************************************************************
 * @Package wg.application.threadLocal.controller
 * @author wg
 * @date 2021/2/22 15:44
 * @version
 * @Copyright
 * @discription 弱引用 垃圾回收器一看到就回收, 起 一次性作用
 *************************************************************/
public class WeakReference {



    public static void main(String[] args) {
        java.lang.ref.WeakReference<M> w = new java.lang.ref.WeakReference<>(new M());

        System.out.println(w.get());
        System.gc();
        System.out.println(w.get());

        ThreadLocal<M> mtl = new ThreadLocal<>();
        mtl.set(new M());
        mtl.remove();
    }
}
