package wg.application;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import org.apache.hadoop.security.SecurityUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.FileUtil;

import java.io.File;
import java.net.InetAddress;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 9:44 2022/9/14
 * @updateTime: 9:44 2022/9/14
 ************************************************************************/
@SpringBootTest
public class SecurityTest {

    @Test
    public void test() {
        try {
            String localHostName = SecurityUtil.getLocalHostName();
            System.out.println(localHostName); // DESKTOP-8ST7NBP

            InetAddress inetAddress = SecurityUtil.getByName(localHostName);
            System.out.println(inetAddress);  // DESKTOP-8ST7NBP/10.12.12.98

            MD5 md5 = new MD5();
            String digestHex16 = md5.digestHex16(new File("H:\\java-project\\test\\src\\test\\java\\wg\\application\\FileTest.java"));
            System.out.println(digestHex16); // 7a68e4ae7d4ac3aa

            File file = new File("H:\\java-project\\test\\src\\test\\java\\wg\\application\\FileTest.java");
            byte[] bytes = DigestUtil.sha256(file);
            String sha256Hex = DigestUtil.sha256Hex(file);
            System.out.println(sha256Hex); // da112f88e67482363bab30259fd6f6ace7aa1929addb5eef97f51e076d4257f4

            String hexHash = FileUtil.getSha256Hex(file);
            System.out.println(hexHash); // da112f88e67482363bab30259fd6f6ace7aa1929addb5eef97f51e076d4257f4
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
