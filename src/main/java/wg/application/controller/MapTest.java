package wg.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import wg.application.interfaceWg.InterfaceTest1;

import java.util.*;

/************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/6/17 10:23
 * @version
 * @Copyright
 ************************************************************/
@Controller
@RequestMapping(value = "/mapTest")
public class MapTest {


    /******************************************************************
     * clear,方法
     * Map,Collection 接口
     * @author: wg
     * @time: 2020/6/17 10:37
     ******************************************************************/
    @GetMapping(value = "/mapTest1")
    @ResponseBody
    public String mapTest1() {
        Map map = new HashMap<>();
        map.put(1, "name");
        map.put(2, "age");
        map.put("qiye", 123.456);

        Collection values = new ArrayList(map.values());
        System.out.println(Arrays.toString(values.toArray()));

        System.out.println(map);
        map.clear();


        return map.size() + " :: " + map + " ::";
    }

    @GetMapping(value = "/mapTest2")
    @ResponseBody
    public String mapTest2() {
        InterfaceTest1 wg = new WgTestInterfaceImpl();
        String name = wg.getName();
        Collection names = wg.getNames();

        for (Object o : names) {

            System.out.println(o);
        }

        return wg.toString();
    }

}

class WgTestInterfaceImpl implements InterfaceTest1 {

    @Override
    public String getName() {
        return "wg";
    }

    @Override
    public Collection getNames() {
        Collection set = new LinkedHashSet();

        set.add("wg");
        set.add(1);
        set.add(0);


        return set;
    }


}

