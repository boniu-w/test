package wg.application;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.FileUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

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


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
