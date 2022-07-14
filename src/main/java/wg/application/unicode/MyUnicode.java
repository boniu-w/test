package wg.application.unicode;

import java.util.Arrays;

public class MyUnicode {

    public static void main(String[] args) {
        try {
            test1();
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
        }
        // test1();
        test2();
    }

    public static void test1() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = new String[10];
        for (int i = 0; i < 10; i++) {
            stringBuilder = new StringBuilder();

            // String s = WgUtil.toHalfAngle(stringBuilder.append("\uFF3C").toString());
            strings[i] = stringBuilder.append("4dc").append(i).toString();
            System.out.println(strings[i]);
        }
        System.out.println(Arrays.toString(strings));
        String s = "\uFF3C";
        System.out.println(s);

        System.out.println("\u4dc5");
    }

    public static void test2() {
        String s = "\u00e0";
        System.out.println(s);

    }

    // public static void test3() throws Exception {
    //     try {
    //         // double x = Math.abs(-123.45);
    //         double x = Math.round(-123.45);
    //         assert x >= 0 : "x must >= 0";
    //         System.out.println(x);
    //     } catch (Exception e) {
    //         System.out.println("error=" + e.getMessage());
    //     }
    // }

}
