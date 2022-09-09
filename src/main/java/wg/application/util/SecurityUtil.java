package wg.application.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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

    public static void main(String[] args) throws DecoderException {
        String salt = RandomStringUtils.randomAlphanumeric(20);
        System.out.println(salt);

    }
}
