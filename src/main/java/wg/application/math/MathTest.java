package wg.application.math;

import wg.application.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 15:21 2022/4/7
 * @updateTime: 15:21 2022/4/7
 ************************************************************************/
public class MathTest {


    public static void main(String[] args) {
        long pow = 0L;

        int[] a = new int[63];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        // int[] a = {0, 1, 3, 31};
        for (int j : a) {
            pow += 1L << j;
            System.out.println(pow);
        }

        List<Integer> list = MathUtil.log2(pow);
        list.forEach(System.out::println);
    }

    public static void zhiShu(int val) {
        String binaryString = Integer.toBinaryString(val);

        System.out.println("binaryString: " + binaryString);

    }

    public static void test(int val) {
        String binaryString = Integer.toBinaryString(val);

        System.out.println("binaryString: " + binaryString);
        ArrayList<Integer> list = new ArrayList<>();
        int ind = 0;
        do {
            if (val % 2 == 1) {
                list.add(ind);
            }
            ind++;
            val = val >> 1;
        } while (val > 0);

        System.out.println("--------");
        list.forEach(System.out::println);
    }

    public static void test1(Long val) {
        ArrayList<Integer> list = new ArrayList<>();
        int ind = 0;
        do {
            if (val % 2 == 1) {
                list.add(ind);
            }
            ind++;
            val = val >> 1;
        } while (val > 0);

        System.out.println("--------");
        list.forEach(System.out::println);
    }

    private static String pow2(int n) {
        StringBuilder res = new StringBuilder("1");
        // 重复N次
        for (int i = 0; i < n; i++) {
            // 进位标志，每轮清零
            int temp = 0;
            // result中的字符，从前往后逐位*2
            for (int j = res.length() - 1; j >= 0; j--) {
                // 乘法运算,需要加上进位
                temp = ((res.charAt(j) - '0') << 1) + temp / 10;
                // 替换此位结果
                res.setCharAt(j, (char) (temp % 10 + '0'));
            }
            // 产生进位则需添加新的数字
            if (temp / 10 >= 1)
                res.insert(0, '1');
        }

        return res.toString();
    }

}
