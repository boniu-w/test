package wg.application.security;

import java.util.Random;

/************************************************************************
 * @description: 加密
 * @author: wg
 * @date: 10:41  2021/10/12
 ************************************************************************/
public class CommonEncryption {
    private static final int i;

    static {
        Random random = new Random();
        i = random.nextInt();
    }

    public static int getStaticDigit() {
        return i;
    }

    /************************************************************************
     * @description: 移位加密 加 位数;  A-Z 65-90 ; a-z 97-122
     * @author: wg
     * @date: 10:47  2021/10/12
     ************************************************************************/
    public static String displacementEncryption(String str, int digit) {
        char[] chars = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char aChar : chars) {
            char c = (char) ((int) aChar + digit);
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String displacementEncryption(String str) {
        char[] chars = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char aChar : chars) {
            char c = (char) ((int) aChar + i);
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    /************************************************************************
     * @description: 移位解密 减 位数
     * @author: wg
     * @date: 11:07  2021/10/12
     ************************************************************************/
    public static String displacementDecrypt(String str, int digit) {
        char[] chars = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char aChar : chars) {
            char c = (char) ((int) aChar - digit);
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String displacementDecrypt(String str) {
        char[] chars = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char aChar : chars) {
            char c = (char) ((int) aChar - i);
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}
