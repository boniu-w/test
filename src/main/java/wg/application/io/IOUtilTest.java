package wg.application.io;

import org.apache.commons.io.IOUtils;

import java.io.*;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:15 2022/9/13
 * @updateTime: 10:15 2022/9/13
 ************************************************************************/
public class IOUtilTest {

    public static void main(String[] args) {
        File file0 = new File("C:\\Users\\wg\\Desktop\\sys_role.sql");
        File file1 = new File("C:\\Users\\wg\\Desktop\\picture.sql");
        try {
            FileInputStream fileInputStream = new FileInputStream(file0);
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            int copy = IOUtils.copy(fileInputStream, fileOutputStream);
            System.out.println(copy);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
