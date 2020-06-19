package wg.application;

import com.alibaba.druid.support.json.JSONUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.util.WgJsonUtil;

import java.util.HashMap;

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

}
