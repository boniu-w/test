package wg.application.controller;

import java.util.LinkedList;

/************************************************************************
 * @description: 栈
 * @author: wg
 * @date: 13:50  2021/10/11
 ************************************************************************/
public class StackTest {

    public static void main(String[] args) {
        test();
    }

    /************************************************************************
     * @description: pop=removeFirst push=addToFirst
     * @author: wg
     * @date: 13:52  2021/10/11
     ************************************************************************/
    public static void test() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(0, 11);
        list.add(1, 22);
        list.add(2, 33);

        System.out.println(list);

        // 移除头位元素, list减少一位
        list.pop();
        System.out.println(list);

        // 把元素放入头位, list增长一位
        list.push(44);
        System.out.println(list);

        // 检出头位元素
        System.out.println(list.peek());
    }
}
