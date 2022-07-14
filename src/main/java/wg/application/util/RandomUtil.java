package wg.application.util;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    /************************************************************************
     * @author: wg
     * @description: 从字典表 dictstring 里 随机取出 length 个字符
     * @params:
     * @return:
     * @createTime: 11:18  2022/3/11
     * @updateTime: 11:18  2022/3/11
     ************************************************************************/
    public static String randomString(String dictString, int length) {
        if (StringUtils.isEmpty(dictString)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(length);
            if (length < 1) {
                length = 1;
            }

            int baseLength = dictString.length();

            for (int i = 0; i < length; ++i) {
                ThreadLocalRandom current = ThreadLocalRandom.current();
                int number = current.nextInt(baseLength);
                // int number = randomInt(baseLength);
                sb.append(dictString.charAt(number));
            }

            return sb.toString();
        }
    }
}
