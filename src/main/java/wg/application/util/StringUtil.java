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
}
