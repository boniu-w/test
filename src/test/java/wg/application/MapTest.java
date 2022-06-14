package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

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

    /**
     * 取 map 值最大的键
     */
    @Test
    public void test2(){
        Map<String, Integer> map = new HashMap();
        map.put("1", 8);
        map.put("2", 12);
        map.put("3", 53);
        map.put("4", 33);
        map.put("5", 11);
        map.put("6", 3);
        map.put("7", 3);
        List<Map.Entry<String,Integer>> list = new ArrayList(map.entrySet());
        Collections.sort(list, (o1, o2) -> (o2.getValue() - o1.getValue()));

        String key = list.get(0).getKey();
        System.out.println(key);
    }
}
