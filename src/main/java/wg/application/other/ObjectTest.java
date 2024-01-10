package wg.application.other;

import java.util.HashMap;
import java.util.Map;

/************************************************************************
 * author: wg
 * description: ObjectTest 
 * createTime: 14:52 2024/1/3
 * updateTime: 14:52 2024/1/3
 ************************************************************************/
public class ObjectTest {

    private Long id;
    private String name;
    private Map<Long, String> map;

    public void test() {
        this.id = 1L;
        this.name = "123";
        this.map = new HashMap<>();
        this.map.put(this.id, this.name);

        System.out.println("this.map = " + this.map);
    }
}
