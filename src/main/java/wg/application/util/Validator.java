package wg.application.util;

import cn.hutool.core.lang.PatternPool;

import java.util.regex.Pattern;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 16:04 2022/8/22
 * @updateTime: 16:04 2022/8/22
 ************************************************************************/
public class Validator {
    public static final Pattern IPV4 = PatternPool.IPV4;

    public static boolean isIpv4(CharSequence value) {
        return isMatchRegex(IPV4, value);
    }

    public static boolean isMatchRegex(Pattern pattern, CharSequence value) {
        return RegexUtil.isMatch(pattern, value);
    }
}
