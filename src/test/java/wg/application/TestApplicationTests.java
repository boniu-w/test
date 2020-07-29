package wg.application;

import com.alibaba.druid.support.json.JSONUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.WgJsonUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class TestApplicationTests {


    /****************************************************************
     * 下面两个方法都是将hashmap 转成 字符串 ,自己写的util 不行;
     * @author: wg
     * @time: 2020/6/19 16:07
     ****************************************************************/
    @Test
    public void contextLoads() {

        System.out.println("ddddddd");

        // hashmap 转字符串
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "123123");
        hashMap.put("2", "123123");

        String s = WgJsonUtil.hashMapToJsonString(hashMap);
        System.out.println(s);


        // 将字符串写入 文件 通过打印流
        String path = "D:\\ideaprojects\\test\\src\\main\\resources\\static\\json\\jsonData.json";
        WgJsonUtil.jsonDataToFile(path, s);

    }


    @Test
    public void jsonTest() {
        // hashmap 转 字符串
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "123123");
        hashMap.put("age", "123123");

        String jsonString = JSONUtils.toJSONString(hashMap);

        System.out.println(jsonString);

        // 将字符串写入 文件 通过buffer流 D:\ideaprojects\test\src\main\resources\static\json
        String path = "D:\\ideaprojects\\test\\src\\main\\resources\\static\\json\\jsonData.json";
        WgJsonUtil.jsonDataToFile2(path, jsonString);

    }

    @Test
    public void defaultValue() {
        boolean b;
        b = 0 < 0;
        System.out.println(b);
    }


    @Test
    public void dateTest() {
        wg.application.controller.Test test = new wg.application.controller.Test();

        test.dateTest();
    }

    @Test
    public void instanceTest() {
        String s = "123";

        if (s.getClass().isInstance(String.class)) {  // false
            System.out.println("...........true");
        }

        if (String.class.isInstance(s)) {  // true
            System.out.println(true);
        }

        if (s instanceof String) {  // true
            System.out.println("<<<<<<<<<<<<<<<<<");
        }

    }

    @Test
    public void duboTest() {
        // 局
        List<String> list = new ArrayList<>();
        list.add("300028,319485,542246,708949");
        list.add("300028,319485,542246,708949");
        list.add("300028,319485,542246,708949");
        list.add("300028,319485,542246,1");

        //int frequency = 0;
        //HashMap<Object, Object> map = new HashMap<>();
        //for (int i = 0; i < list.size(); i++) {
        //    frequency = Collections.frequency(list, list.get(i));
        //    map.put(list.get(i), frequency);
        //}
        //
        //System.out.println(map);

        // 人员id
        List<String> useridList = new ArrayList<>();
        useridList.add("300028");
        useridList.add("319485");
        useridList.add("708949");
        useridList.add("542246");
        useridList.add("1");


        int count = 0;
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int j = 0; j < useridList.size(); j++) {
            for (int i = 0; i < list.size(); i++) {
                String[] playId = list.get(i).split(",");

                for (int k = 0; k < playId.length; k++) {
                    if (playId[k].equals(useridList.get(j))){
                        count++;
                    }
                }

                //String playids = list.get(i);
                //String reg = "" + useridList.get(j) + "";
                //Pattern compile = Pattern.compile(reg);
                //Matcher matcher = compile.matcher(playids);
                //boolean b = matcher.find();
                //if (b == true) {
                //    count++;
                //}
            }
            hashMap.put(useridList.get(j), count);
            count = 0;
        }
        System.out.println(hashMap);
    }

}
