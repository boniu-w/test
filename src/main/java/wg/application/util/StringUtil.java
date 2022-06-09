package wg.application.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    /************************************************************************
     * @description: 判断字符串是否以 斜杠 开头, 不是的话加 斜杠
     * @author: wg
     * @date: 10:29  2022/1/4
     * @params:
     * @return:
     ************************************************************************/
    public static String slashPattern(String str) {
        if (StringUtils.isBlank(str)) return "";
        char c = str.charAt(0);
        char ch = '/';
        if (c == ch) {
            return str;
        } else {
            return ch + str;
        }
    }

    /************************************************************************
     * @author: wg
     * @description: 转 unicode
     * @params:
     * @return:
     * @createTime: 14:53  2022/4/25
     * @updateTime: 14:53  2022/4/25
     ************************************************************************/
    public static String toUnicode(String source) {
        StringBuffer sb = new StringBuffer();
        char[] source_char = source.toCharArray();
        String unicode = null;
        for (char c : source_char) {
            unicode = Integer.toHexString(c);
            if (unicode.length() <= 2) {
                unicode = "00" + unicode;
            }
            sb.append("\\u").append(unicode);
        }
        return sb.toString();
    }

    /************************************************************************
     * @author: wg
     * @description: 解析 unicode
     * @params:
     * @return:
     * @createTime: 14:53  2022/4/25
     * @updateTime: 14:53  2022/4/25
     ************************************************************************/
    public static String decodeUnicode(String unicode) {
        StringBuilder sb = new StringBuilder();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            sb.append((char) data);
        }
        return sb.toString();
    }

    /************************************************************************
     * @author: wg
     * @description: 解析 unicode
     * @params:
     * @return:
     * @createTime: 14:54  2022/4/25
     * @updateTime: 14:54  2022/4/25
     ************************************************************************/
    public static String decodeUnicode2(String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuilder builder = new StringBuilder();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = null;
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16);
            builder.append(letter);
            start = end;
        }
        return builder.toString();
    }

    /************************************************************************
     * @author: wg
     * @description: 最长公共前缀
     * @params:
     * @return:
     * @createTime: 16:59  2022/5/5
     * @updateTime: 16:59  2022/5/5
     ************************************************************************/
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0)
            return "";
        String ans = strs[0];
        for (int i = 1; i < strs.length; i++) {
            int j = 0;
            for (; j < ans.length() && j < strs[i].length(); j++) {
                if (ans.charAt(j) != strs[i].charAt(j))
                    break;
            }
            ans = ans.substring(0, j);
            if (ans.equals(""))
                return ans;
        }
        return ans;
    }

    /************************************************************************
     * @author: wg
     * @description: 在数字前面加 n 个 0
     * @params:
     * @return:
     * @createTime: 15:35  2022/6/8
     * @updateTime: 15:35  2022/6/8
     ************************************************************************/
    public static String paddingZero(int numeral, int length) {
        String codeFormat = "%0" + String.valueOf(length) + "d"; // %04d

        return String.format(codeFormat, numeral);
    }


}
