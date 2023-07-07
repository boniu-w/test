package wg.application.util;

import org.apache.commons.lang3.StringUtils;
import wg.application.exception.WgException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
    /************************************************************************
     * @description: 下划线转驼峰
     * @author: wg
     * @date: 13:51  2021/11/11
     * @params:
     * @return:
     ************************************************************************/
    public static String getHumpString(String str) {
        String[] s = str.split("_");
        StringBuilder stringBuilder = new StringBuilder(s[0].toLowerCase());
        for (int k = 0; k < s.length - 1; k++) {
            
            stringBuilder.append(s[k + 1].substring(0, 1).toUpperCase()).append(s[k + 1].substring(1));
        }
        
        return stringBuilder.toString();
    }
    
    public static String toHumpString(String str) {
        String[] s = str.split("_");
        String[] newStrs = new String[s.length];
        for (int i = 0; i < newStrs.length; i++) {
            newStrs[i] = s[i].toLowerCase();
        }
        StringBuilder stringBuilder = new StringBuilder(newStrs[0]);
        for (int k = 0; k < newStrs.length - 1; k++) {
            stringBuilder.append(newStrs[k + 1].substring(0, 1).toUpperCase()).append(newStrs[k + 1].substring(1));
        }
        
        return stringBuilder.toString();
    }
    
    /************************************************************************
     * @description: 驼峰转下划线
     * @author:
     * @date: 11:22  2021/9/1
     ************************************************************************/
    public static String humpToLine(String str) {
        if (StringUtils.isBlank(str)) return "";
        char firstChar = str.charAt(0);
        if (!Character.isLowerCase(firstChar)) {
            // 将首字母转换为小写，并与剩余部分拼接
            str = Character.toLowerCase(firstChar) + str.substring(1);
        }
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        
        return sb.toString();
    }
    
    /************************************************************************
     * @description:
     * 转全角的方法(SBC case) 半角转全角
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     * @author: wg
     * @date: 11:09  2021/11/12
     * @params: 任意字符串
     * @return: 全角字符串
     ************************************************************************/
    public static String toFullAngle(String input) {
        //半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }
    
    /************************************************************************
     * @description: 任意字符串 转 半角
     * @author: wg
     * @date: 11:11  2021/11/12
     * @params:
     * @return:
     ************************************************************************/
    public static String toHalfAngle(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                //其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
    
    /************************************************************************
     * @description: 按 指定长度 分割字符串
     * @author: wg
     * @date: 14:47  2021/11/19
     * @params:
     * @return:
     ************************************************************************/
    public static String[] subStringByFixedLength(String str, int len) {
        if (str.length() <= len) {
            return new String[]{str};
        }
        
        String halfAngle = toHalfAngle(str);
        char[] chars = halfAngle.toCharArray();
        int i = chars.length % len == 0 ? chars.length / len : chars.length / len + 1;
        String[] targetString = new String[i];
        String substring = null;
        for (int j = 0; j < i; ) {
            if ((j + 1) * len > chars.length) {
                substring = halfAngle.substring(j * len, chars.length);
                targetString[j] = substring;
                break;
            }
            substring = halfAngle.substring(j * len, (j + 1) * len); // 012345
            targetString[j] = substring;
            j++;
        }
        
        return targetString;
    }
    
    /************************************************************************
     * @description: 每行显示 指定个数的字符串
     * @author: wg
     * @date: 9:57  2021/11/26
     * @params:
     * @return:
     ************************************************************************/
    public static String[] arraySplitOutput(String[] strs, int m) {
        int len = strs.length;
        int line = len / m;
        for (int i = 0; i < line; i++) {
            for (int j = m * i; j < m * (i + 1); j++) {
                System.out.print(String.format("%-10s", strs[j]));
            }
            System.out.println();
        }
        for (int i = m * line; i < len; i++) {
            System.out.print(String.format("%-10s", strs[i]));
        }
        return null;
    }
    
    /************************************************************************
     * @author: wg
     * @description: 获取字符的 asc 码 值
     * @params:
     * @return:
     * @createTime: 11:43  2022/9/8
     * @updateTime: 11:43  2022/9/8
     ************************************************************************/
    public Integer getAsc(Character character) {
        Integer integer = Integer.valueOf(character);
        return integer;
    }
    
    public static Character toChar(int asc) {
        return (char) asc;
    }
    
    /************************************************************************
     * @author: wg
     * @description: 判断是全角还是半角
     * @params:
     * @return:
     * @createTime: 12:15  2022/9/8
     * @updateTime: 12:15  2022/9/8
     ************************************************************************/
    public boolean isHalf(Character character) {
        Integer integer = Integer.valueOf(character);
        return integer >= 33 && integer <= 126;
    }
    
    /************************************************************************
     * @author: wg
     * @description: 测试 事务 调用的工具类里有 try catch
     * 结论: 调用的工具类里有 try catch , 发生异常时 事务不生效, 不会回滚
     * 因此, 工具类也需要抛出异常
     * @params:
     * @return:
     * @createTime: 9:37  2022/11/29
     * @updateTime: 9:37  2022/11/29
     ************************************************************************/
    public static void testTransaction() {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void testTransactionThrow() {
        throw new WgException(500);
    }
    
    /************************************************************************
     * @author: wg
     * @description: 二进制字符串 转十进制 int
     * @params:
     * @return:
     * @createTime: 15:31  2023/1/6
     * @updateTime: 15:31  2023/1/6
     ************************************************************************/
    public static int binaryString2DecimalInt(String binaryString) {
        int sum = 0;
        for (int i = 0; i < binaryString.length(); i++) {
            char ch = binaryString.charAt(i);
            if (ch > '2' || ch < '0') throw new NumberFormatException(String.valueOf(i));
            sum = sum * 2 + (binaryString.charAt(i) - '0');
        }
        return sum;
    }
    
    /************************************************************************
     * @author: wg
     * @description: 将输入的字符 转换为16进制字符串
     * @params:
     * @return:
     * @createTime: 15:30  2023/2/22
     * @updateTime: 15:30  2023/2/22
     ************************************************************************/
    public static String hash256(String input) {
        try {
            // 创建SHA-256哈希函数实例
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            
            // 将输入转换为字节数组并进行哈希计算
            byte[] hashBytes = sha256.digest(input.getBytes());
            
            // 将哈希值转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /************************************************************************
     * @author: wg
     * @description: 两个数字型字符串是否相等
     * @params:
     * @return:
     * @createTime: 17:55  2023/3/8
     * @updateTime: 17:55  2023/3/8
     ************************************************************************/
    public static boolean numberEquals(String str1, String str2) {
        double num1 = Double.parseDouble(str1);
        double num2 = Double.parseDouble(str2);
        return Double.compare(num1, num2) == 0;
    }
    
    public static boolean isBlank(final CharSequence cs) {
        if (Objects.equals(cs, "null") || Objects.equals(cs, "Null") || Objects.equals(cs, "NULL")) return true;
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }
    
    /************************************************************************
     * @description: 判断是否是数字
     * @author: wg
     * @date: 15:50  2021/12/14
     * @params:
     * @return:
     ************************************************************************/
    public static boolean isNumber(String val) {
        if (null == val || "".equals(val)) {
            return false;
        }
        
        String rex = "^[+-]?\\d*\\.?\\d*$";
        boolean numbMatch = Pattern.matches(rex, val);
        if (numbMatch) {
            return numbMatch;
        }
        
        rex = "^[+-]?\\d+\\.?\\d*[Ee]*[+-]*\\d+$";
        boolean compile = Pattern.matches(rex, val);
        if (compile) {
            return compile;
        }
        return false;
    }
    
    public static boolean isNumber(Object val) {
        if (null == val || "".equals(val)) {
            return false;
        }
        
        String rex = "^[+-]?\\d*\\.?\\d*$";
        boolean numbMatch = Pattern.matches(rex, Objects.toString(val));
        if (numbMatch) {
            return numbMatch;
        }
        
        rex = "^[+-]?\\d+\\.?\\d*[Ee]*[+-]*\\d+$";
        boolean compile = Pattern.matches(rex, Objects.toString(val));
        if (compile) {
            return compile;
        }
        return false;
    }
}
