package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.FileUtil;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:20 2022/4/27
 * @updateTime: 10:20 2022/4/27
 ************************************************************************/
@SpringBootTest
public class FileTest {

    @Test
    public void test1(){
        String path="12312/sdf.jpg";
        String s = FileUtil.reviseFileName(path);
        System.out.println(s);
    }
}
