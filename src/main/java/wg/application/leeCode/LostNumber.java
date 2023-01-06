package wg.application.leeCode;

/************************************************************************
 * @author: wg
 * @description: 消失的数字
 * @createTime: 16:37 2022/11/8
 * @updateTime: 16:37 2022/11/8
 ************************************************************************/
public class LostNumber {

    /************************************************************************
     * @author: wg
     * @description:
     * 思路: 把一块石头, 先完整过一遍, 没过一个数字包上一层颜色,  然后再剥掉, 最后哪个没被剥掉, 哪个就是缺失的
     * @params:
     * @return:
     * @createTime: 9:43  2023/1/6
     * @updateTime: 9:43  2023/1/6
     ************************************************************************/
    public static void main(String[] args) {
        // int[] a = {0, 1, 2, 3, 4, 6};
        int[] a = {5, 6, 7, 8, 10};
        int x = execute(a);
        System.out.println();
        System.out.println(x);
        // t();
    }

    public static int execute(int[] a) {
        int x = 0;

        for (int i = 5; i <= (a.length + 5); i++) {
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

    public static void t() {
        char a = 'a';
        char b = 'b';
        int i = a ^ b;
        Character character = new Character(a);

        System.out.println(i);

        // string 不能异或
        // String c= "c";
        // String d="d";
        // int j= c ^d;
    }
}
