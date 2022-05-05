package wg.application.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 13:49 2022/5/5
 * @updateTime: 13:49 2022/5/5
 ************************************************************************/
public class SecurityUtil {

    public static void encode() throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    }

}
