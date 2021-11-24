package wg.application.unicode;

import wg.application.util.WgUtil;

import java.util.Arrays;

public class MyUnicode {

    public static void main(String[] args) {
        test1();
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
}
