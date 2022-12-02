package wg.application;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:20 2022/9/13
 * @updateTime: 10:20 2022/9/13
 ************************************************************************/
@SpringBootTest
public class IOTest {

    @Test
    public void apacheCommonsIoTest(){

        File file0 = new File("C:\\Users\\wg\\Desktop\\sys_role.sql");
        File file1 = new File("C:\\Users\\wg\\Desktop\\picture.sql");
        try {
            FileInputStream fileInputStream = new FileInputStream(file0);
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            int copy = IOUtils.copy(fileInputStream, fileOutputStream); // 返回 the number of bytes copied, or -1 if > Integer.MAX_VALUE
            System.out.println(copy);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
