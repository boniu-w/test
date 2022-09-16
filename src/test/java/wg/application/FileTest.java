package wg.application;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.catalina.security.SecurityUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.FileUtil;
import wg.application.util.ZipFilesUtil;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipOutputStream;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:20 2022/4/27
 * @updateTime: 10:20 2022/4/27
 ************************************************************************/
@SpringBootTest
public class FileTest {

    @Test
    public void test1() {
        String path = "12312/sdf.jpg";
        String s = FileUtil.reviseFileName(path);
        System.out.println(s);
    }

    @Test
    public void testGuava() {
        File file = new File("H:\\java-project\\test\\src\\test\\java\\wg\\application\\FileTest.java");
        try {
            List<String> strings = Files.readLines(file, StandardCharsets.ISO_8859_1);
            strings.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIo() {
        try {
            OutputStream outputStream = new FileOutputStream("");
            byte[] bytes = new byte[1024];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testApacheCommonFileUtil(){
        File file = new File("H:\\java-project\\test\\src\\test\\java\\wg\\application\\FileTest.java");
        File[] files = ArrayUtils.toArray(file);
        try {
            URL[] urls = FileUtils.toURLs(files);
            Arrays.stream(urls).forEach(System.out::println);
            for (URL url : urls) {
                url.getFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void zipTest(){
        File file = new File("H:\\java-project\\test\\src\\test\\java\\wg\\application\\FileTest.java");
        // ZipOutputStream zipOutputStream = new ZipOutputStream();
        // ZipFilesUtil.compress(file, "H:\\");
    }
}
