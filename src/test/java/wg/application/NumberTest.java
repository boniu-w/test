package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.StringUtil;

/*****************************************
 * description:
 * date: 13:44 2021/7/26
 * auth: wg
 *****************************************/
@SpringBootTest
public class NumberTest {
    
    @Test
    public void test() {
        String a = "beiPinBeiJianKuCunTongJiBiao";
        String s = StringUtil.humpToLine(a);
        System.out.println(s);
    }
}
