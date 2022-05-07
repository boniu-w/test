package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 16:02 2022/5/7
 * @updateTime: 16:02 2022/5/7
 ************************************************************************/
@SpringBootTest
public class MapTest {

    @Test
    public void test1(){
        HashMap<String, Object> params = new HashMap<>();

        HashMap<String, Object> filterMap = new HashMap<>();

        filterMap.put("a", params.get("a"));

        System.out.println(filterMap);
    }
}
