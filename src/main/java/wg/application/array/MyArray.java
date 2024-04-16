package wg.application.array;

import java.util.Arrays;

public class MyArray {

    public static void main(String[] args) {
         testClone();
        testPP();
    }

    /************************************************************************
     * @description: clone
     * @author: wg
     * @date: 13:34  2021/11/23
     * @params:
     * @return:
     ************************************************************************/
    public static void testClone() {
        String[] strings = new String[3]; // [null, null, null]
        for (int i = 0; i < 3; i++) {
            if (i == 2) {
                strings[i] = null;
                break;
            }
            strings[i] = Integer.toString(i);
        }

        strings = strings.clone();

        System.out.println(Arrays.toString(strings)); // [0, 1, null]

        // System.out.println(Arrays.toString(clone));
    }

    /**
     * @author: wg
     * @description: 测试 i++ 与 ++i
     * @params:
     * @return:
     * @createTime: 14:09  2024/4/16
     * @updateTime: 14:09  2024/4/16
     */
    public static void testPP() {
        int[] arr = {2, 1, 6, 5, 9, 8, 2020, 199};

        int t = 0;
        int temp = arr[t++];
        System.out.println("temp = " + temp); // 2
        System.out.println("t = " + t); // 1

        // arr[i++] = 2
        // arr[i++] = 1
        // arr[i++] = 6
        // arr[i++] = 5
        // arr[i++] = 9
        // arr[i++] = 8
        // arr[i++] = 2020
        // arr[i++] = 199
        for (int i = 0; i < arr.length; ) {
            System.out.println("arr[i++] = " + arr[i++]);
        }

        // arr[++i] = 2
        // arr[++i] = 1
        // arr[++i] = 6
        // arr[++i] = 5
        // arr[++i] = 9
        // arr[++i] = 8
        // arr[++i] = 2020
        // arr[++i] = 199
        for (int i = -1; i < arr.length - 1; ) {
            System.out.println("arr[++i] = " + arr[++i]);
        }
    }
}
