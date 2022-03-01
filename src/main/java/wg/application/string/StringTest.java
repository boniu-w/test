package wg.application.string;

import com.alibaba.fastjson.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StringTest {
    public static void main(String[] args) {
        String s = "getRiskAssessmentData".toLowerCase();
        System.out.println(s);

        String s1 = (String) null;
        // System.out.println(s1.length()); // NullPointerException

        System.out.println("---------------------");

        testBit();
    }

    public static void testBit() {

        byte b = Byte.parseByte("-128");
        System.out.println(b);

        byte[] bits = byteToBitOfArray(b);
        System.out.println(Arrays.toString(bits));
        System.out.println("-------------");

        String a = "你好";
        String s = Integer.toBinaryString(100);
        System.out.println(s);

        byte[] bits1 = a.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < bits1.length; i++) {
            byte[] bits2 = byteToBitOfArray(bits1[i]);
            System.out.println(Arrays.toString(bits2));
        }
    }

    /************************************************************************
     * @description: byte -> bit (-128-127)
     * 字节 转 比特
     * 数组长度值为8，每个值代表bit，即8个bit。bit7 -> bit0
     * bit数组，bit7 -> bit0
     * @author: wg
     * @date: 15:38  2021/12/20
     * @params:
     * @return:
     ************************************************************************/
    public static byte[] byteToBitOfArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }

}
