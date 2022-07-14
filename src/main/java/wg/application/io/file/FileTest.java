package wg.application.io.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class FileTest {

    public static void main(String[] args) {
        // Random r = new Random(1);
        // for (int i = 0; i < 100; i++) {
        //     System.out.println(r.nextInt(100));
        // }
        md5Test();
    }

    /************************************************************************
     * @description: 文件的 md5 值, 可用于验证文件是否被改动过
     * @author: wg
     * @date: 10:57  2021/11/26
     * @params:
     * @return:
     ************************************************************************/
    public static void md5Test() {
        File file = new File("H:/java学习/java-study/java基础.md");
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] bytes = FileUtils.readFileToByteArray(file);
            md5.update(bytes);
            byte[] digest = md5.digest();

            // byte[] digest = md5.digest(bytes);
            String md5val = new BigInteger(1, digest).toString(16);
            System.out.println(md5val);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }
}
