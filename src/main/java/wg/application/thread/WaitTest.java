package wg.application.thread;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class WaitTest {

    private List synchronizedList;

    public WaitTest() {
        // 创建一个同步列表
        synchronizedList = Collections.synchronizedList(new LinkedList());
    }

    // 删除列表中的元素
    public String removeElement() throws InterruptedException {
        synchronized (synchronizedList) {

            // 列表为空就等待
            while (synchronizedList.isEmpty()) {
                System.out.println("List is empty...");
                synchronizedList.wait();
                System.out.println("Waiting...");
            }
            String element = (String) synchronizedList.remove(0);

            return element;
        }
    }

    // 添加元素到列表
    public void addElement(String element) {
        System.out.println("Opening...");
        synchronized (synchronizedList) {

            // 添加一个元素，并通知元素已存在
            synchronizedList.add(element);
            System.out.println("New Element:'" + element + "'");

            synchronizedList.notifyAll();
            System.out.println("notifyAll called!");
        }
        System.out.println("Closing...");
    }

    public static void main(String[] args) {
        final WaitTest demo = new WaitTest();

        Runnable runA = new Runnable() {

            public void run() {
                try {
                    String item = demo.removeElement();
                    System.out.println("" + item);
                } catch (InterruptedException ix) {
                    System.out.println("Interrupted Exception!");
                } catch (Exception x) {
                    System.out.println("Exception thrown.");
                }
            }
        };

        Runnable runB = new Runnable() {

            // 执行添加元素操作，并开始循环
            public void run() {
                demo.addElement("Hello!");
            }
        };

        try {
            Thread threadA1 = new Thread(runA, "Google");
            threadA1.start();

            Thread.sleep(500);

            Thread threadA2 = new Thread(runA, "Runoob");
            threadA2.start();

            Thread.sleep(500);

            Thread threadB = new Thread(runB, "Taobao");
            threadB.start();

            Thread.sleep(1000);

            threadA1.interrupt();
            threadA2.interrupt();
        } catch (InterruptedException x) {
        }
    }
}
