package wg.application.algorithm.hash;

import cn.hutool.core.io.FileUtil;

import java.io.File;
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
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update("Hello".getBytes(StandardCharsets.UTF_8));
            messageDigest.update("World".getBytes(StandardCharsets.UTF_8));
            byte[] digest = messageDigest.digest();
            System.out.println(new BigInteger(1, digest).toString(16));

            messageDigest = MessageDigest.getInstance("SHA-512");
            File file = new File("H:\\img\\img\\wallpaper\\xiaochou.jpeg");
            messageDigest.update(FileUtil.readBytes(file));
            byte[] digest1 = messageDigest.digest();
            String hex = new BigInteger(1, digest1).toString(16);
            System.out.println(hex);
            System.out.println(hex.length());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
