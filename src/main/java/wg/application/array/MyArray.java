package wg.application.array;

import java.util.Arrays;

public class MyArray {

    public static void main(String[] args) {
        testClone();
    }

    /************************************************************************
     * @description: clone
     * @author: wg
     * @date: 13:34  2021/11/23
     * @params:
     * @return:
     ************************************************************************/
    public static void testClone() {
        String[] strings = new String[3];
        for (int i = 0; i < 3; i++) {
            if (i == 2) {
                strings[i] = null;
                break;
            }
            strings[i] = Integer.toString(i);
        }

        strings = strings.clone();

        System.out.println(Arrays.toString(strings));

        // System.out.println(Arrays.toString(clone));
    }
}
