package wg.application.gc;

import wg.application.entity.Student;

import java.util.LinkedList;
import java.util.List;


public class TestHeap {
    public static void main(String[] args) {
        oomTest();
    }

    /************************************************************************
     * @description: 1. 温水煮青蛙内存溢出 -> 往堆里面一直放对象，总会有放不下的
     * 1. 堆大小设置成5M
     * 2.参数：
     * -Xms5m -Xmx5m -XX:PrintGCDetails -verbose:gc
     * @author: wg
     * @date: 10:46  2021/12/20
     * @params:
     * @return: 异常 -> Exception in thread "main" java.lang.OutOfMemoryError:
     *  GC overhead limit exceeded
     ************************************************************************/
    public static void oomTest() {
        List<Object> list = new LinkedList<>();
        int i = 0;
        while (true) {
            i++;
            if (i % 1000 == 0) {
                System.out.println("i:" + i);
            }
            list.add(new Object());
        }
    }

    /************************************************************************
     * @description: 2. 巨婴堆溢出 -> 往堆里面扔一个超过堆大小的对象
     * 1. 堆大小设置成5M
     * 2.参数：
     * -Xms5m -Xmx5m -XX:PrintGCDetails -verbose:gc
     * @author: wg
     * @date: 10:51  2021/12/20
     * @params:
     * @return: 异常 -> Exception in thread "main" java.lang.OutOfMemoryError:
     *          Java heap space
     ************************************************************************/
    public void oomTest02() {
        String[] strings = new String[1000000000];
    }

    /************************************************************************
     * @description: 3. 方法区和常量池溢出
     * -XX:MaxMetaspaceSize=3M
     * @author: wg
     * @date: 10:53  2021/12/20
     * @params:
     * @return: 异常 -> Error occurred during initialization of VM
     * MaxMetaspaceSize is too small.
     * 就是运行的内存都不够
     ************************************************************************/
    public void oomTest03() {
        List<Object> list = new LinkedList<>();
    }

    /************************************************************************
     * @description: 4. 虚拟机栈和本地方法溢出 递归有可能产生栈溢出的情况
     * -Xss256k
     * 默认的是1M
     * @author: wg
     * @date: 10:55  2021/12/20
     * @params:
     * @return: 异常 -> 3536========
     * java.lang.StackOverflowError
     ************************************************************************/
    public static class TestVmStack {
        private int length = 1;

        private void diGui() {
            length++;
            diGui();
        }

        public static void main(String[] args) {
            TestVmStack vm = new TestVmStack();

            try {
                vm.diGui();
            } catch (Throwable e) {
                System.out.println(vm.length + "========");
                e.printStackTrace();
            }
        }
    }

    /************************************************************************
     * @description: 内存泄漏 -> 要释放或者要被垃圾回收的的东西，没有被回收
     * @author: wg
     * @date: 10:57  2021/12/20
     * @params:
     * @return:
     ************************************************************************/
    // 自定义实现一个栈，但是出栈的时候不把对象删除
    public static class UserStack {
        private Object[] elements;
        private int size = 0;//栈顶位置
        private static final int Cap = 16;

        public UserStack() {
            elements = new Object[Cap];
        }

        //放入元素
        public void push(Object o) {
            elements[size++] = o;
        }

        public Object pop() {
            Object o = elements[--size];

            // 要想不内存溢出，要把需要被pop出去的元素置空
            // elements[--size] = null; // clear to let GC do its work
            // 这个就是arrayList源码里面的remove方法

            return o;
        }

        // 这个就是arrayList源码里面的remove方法
        // public E remove(int index) {
        //     rangeCheck(index);
        //
        //     modCount++;
        //     E oldValue = elementData(index);
        //
        //     int numMoved = size - index - 1;
        //     if (numMoved > 0)
        //         System.arraycopy(elementData, index + 1, elementData, index,
        //                 numMoved);
        //     elementData[--size] = null; // clear to let GC do its work
        //
        //     return oldValue;
        // }

        public static void main(String[] args) {
            UserStack u = new UserStack();
            Student student = new Student();
            System.out.println(student);
            u.push(student);
            Object ret = u.pop();
            System.out.println(ret + " ---- ");
        }
    }
}
