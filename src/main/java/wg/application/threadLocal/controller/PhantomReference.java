package wg.application.threadLocal.controller;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

/*************************************************************
 * @Package wg.application.threadLocal.controller
 * @author wg
 * @date 2021/2/22 15:52
 * @version
 * @Copyright
 * @discription 虚引用, 管理堆外内存
 *************************************************************/
public class PhantomReference {

    private static final List<Object> LIST = new LinkedList<>();
    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) {
        java.lang.ref.PhantomReference<M> phantomReference = new java.lang.ref.PhantomReference<>(new M(), QUEUE);

        new Thread(() -> {
            while (true) {
                LIST.add(new byte[1024 * 1024 * 10]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println("phantomReference.get() : " + phantomReference.get());
            }
        }).start();

        // 监视堆外内存
        new Thread(() -> {
            while (true) {
                Reference<? extends M> poll = QUEUE.poll();
                if (poll != null) {
                    System.out.println("--- 虚引用对象被jvm回收了 ---" + poll);
                }
            }
        }).start();
    }
}
