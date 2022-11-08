package wg.application.leeCode;

/************************************************************************
 * @author: wg
 * @description: 消失的数字
 * @createTime: 16:37 2022/11/8
 * @updateTime: 16:37 2022/11/8
 ************************************************************************/
public class LostNumber {

    public static void main(String[] args) {
        int[] a = {0, 1, 2, 3, 4, 6};
        int x = execute(a);
        System.out.println(x);
    }

    public static int execute(int[] a) {
        int x = 0;

        for (int i = 0; i <= a.length; i++) {
            x = x ^ i;
            System.out.println(x);
        }
        System.out.println();

        for (int i = 0; i < a.length; i++) {
            x = x ^ a[i];
            System.out.println(x);
        }

        return x;
    }
}
