package wg.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/7/30 16:06
 * @version
 * @Copyright
 *************************************************************/
@Controller
@RequestMapping(value = "/loopTest")
public class LoopTest {

    /****************************************************************
     * for循环去重 , 10万条数据 耗时 370毫秒左右
     * @author: wg
     * @time: 2020/7/30 17:14
     ****************************************************************/
    @RequestMapping(value = "/loop")
    @ResponseBody
    public List<String> loopTest() {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> duplicateString = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            arrayList.add(Math.floor(Math.random() * 10) + "");
        }

        long l = System.currentTimeMillis();
        // list 去重
        for (int i = 0; i < arrayList.size(); i++) {
            String s = arrayList.get(i);
            for (int j = arrayList.size() - 1; j > i; j--) {
                String s1 = arrayList.get(j);
                if (s.equals(s1)) {
                    //System.out.println("第 " + (j + 1) + " 行的 roleCode 值：" + s + " 已存在，忽略导入");
                    //duplicateString.add(s1);
                    arrayList.remove(j);
                }
            }
            //System.out.println("<<<<>>>>  "+arrayList.size());
        }

        long l1 = System.currentTimeMillis();

        long l2 = l1 - l;
        System.out.println("耗时 "+l2+" 毫秒");
        //System.out.println(arrayList);
        System.out.println(duplicateString);

        return arrayList;
    }

    /****************************************************************
     *  set去重 10万条数据 耗时 4毫秒
     * @author: wg
     * @time: 2020/7/30 17:13
     ****************************************************************/
    @RequestMapping(value = "/loop2")
    @ResponseBody
    public Set<String> loopTest2() {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> duplicateString = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            arrayList.add(Math.floor(Math.random() * 10) + "");
        }

        long l = System.currentTimeMillis();
        // list 去重

        HashSet<String> set = new HashSet<>(arrayList);

        System.out.println(set.size());

        long l1 = System.currentTimeMillis();

        long l2 = l1 - l;
        System.out.println("耗时 "+l2+" 毫秒");
        //System.out.println(arrayList);
        System.out.println(duplicateString);

        return set;
    }

}
