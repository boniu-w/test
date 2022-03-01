package wg.application.datastructure;

import java.util.Stack;

public class DataTest {

    /************************************************************************
     * @description: stack
     * @author: wg
     * @date: 14:05  2021/12/20
     * @params:
     * @return:
     ************************************************************************/
    public void test01() {
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < 5; i++) {
            stack.push(String.valueOf(i));
        }

        System.out.println(stack);

        System.out.println(stack.search("0"));
        System.out.println(stack.search("4"));

        String pop = stack.pop();
        System.out.println(pop);
        System.out.println(stack);
    }
}
