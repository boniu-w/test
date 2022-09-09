package wg.application.util;

import java.util.regex.Pattern;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 16:07 2022/8/22
 * @updateTime: 16:07 2022/8/22
 ************************************************************************/
public class RegexUtil {
    public static boolean isMatch(Pattern pattern, CharSequence content) {
        return content != null && pattern != null && pattern.matcher(content).matches();
    }
}
