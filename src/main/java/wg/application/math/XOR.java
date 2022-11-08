package wg.application.math;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 17:16 2022/11/8
 * @updateTime: 17:16 2022/11/8
 ************************************************************************/
public class XOR {

    public static void main(String[] args) {
        int a = 32;
        int b = 33;
        int i = execute(a, b);
        System.out.println(i);
    }

    public static int execute(int a, int b) {
        int x = a ^ b;
        return x;
    }
}
