package wg.application.leeCode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/************************************************************************
 * @description: 有效符号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']'的字符串 s ，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * @author: wg
 * @date: 11:34  2021/10/11
 ************************************************************************/
public class ValidSymbol {

    public static void main(String[] args) {
        String symbol = "{}[])(";
        String symbol1 = "{[}]";
        boolean valid = isValid(symbol1);
        System.out.println(valid);
    }

    /************************************************************************
     * @description: 栈
     * @author: wg
     * @date: 11:39  2021/10/11
     ************************************************************************/
    public static boolean isValid(String s) {
        int n = s.length();
        if (n % 2 == 1) {
            return false;
        }

        Map<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};
        Deque<Character> stack = new LinkedList<Character>();
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (pairs.containsKey(ch)) {
                if (stack.isEmpty() || stack.peek() != pairs.get(ch)) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }
}
