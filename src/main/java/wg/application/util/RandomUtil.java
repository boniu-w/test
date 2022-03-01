package wg.application.util;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static String randomString(String baseString, int length) {
        if (StringUtils.isEmpty(baseString)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(length);
            if (length < 1) {
                length = 1;
            }

            int baseLength = baseString.length();

            for (int i = 0; i < length; ++i) {
                ThreadLocalRandom current = ThreadLocalRandom.current();
                int number = current.nextInt(baseLength);
                // int number = randomInt(baseLength);
                sb.append(baseString.charAt(number));
            }

            return sb.toString();
        }
    }
}
