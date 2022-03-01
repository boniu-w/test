package wg.application.algorithm.hash;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/************************************************************************
 * @description: md5 sha-256 sha-512 sha-1 ripemd-160
 * @author: wg
 * @date: 9:50  2021/11/26
 * @params:
 * @return:
 ************************************************************************/
public class Test {

    public static void main(String[] args) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("sha512");
            md5.update("Hello".getBytes(StandardCharsets.UTF_8));
            md5.update("World".getBytes(StandardCharsets.UTF_8));
            byte[] digest = md5.digest();
            System.out.println(new BigInteger(1, digest).toString(16));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
