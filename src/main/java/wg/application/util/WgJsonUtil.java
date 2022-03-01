package wg.application.util;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URI;
import java.util.Map;
import java.util.Set;

/*************************************************************
 * @Package wg.application.util
 * @author wg
 * @date 2020/6/19 11:56
 * @version
 * @Copyright
 *************************************************************/
public class WgJsonUtil {

    @Value("${spring.datasource.oracle.url}")
    String url;

    @Value("${spring.datasource.oracle.name}")
    String name;

    @Value("${spring.datasource.oracle.password}")
    String password;

    @Value("${spring.datasource.oracle.driver-class-name}")
    String driverName;


    /****************************************************************
     * 自己写的hashmap 转字符串, 实际工作中不能用,阿里的jsonutil 还是好用
     * @author: wg
     * @time: 2020/6/19 16:23
     ****************************************************************/
    public static String hashMapToJsonString(Map<String, String> hashMap) {

        StringBuilder stringBuilder = new StringBuilder("{");
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(":");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(",");
        }

        String substring = stringBuilder.substring(0, stringBuilder.lastIndexOf(","));

        substring += "}";

        return substring;

    }

    /****************************************************************
     * 打印流 练习
     * @author: wg
     * @time: 2020/6/19 16:24
     ****************************************************************/
    public static void jsonDataToFile(String path, String jsonData) {
        try {
            String s = JSON.toJSONString(jsonData);

            File file = ResourceUtils.getFile(path);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream outputStream = new FileOutputStream(file);

            PrintStream printStream = new PrintStream(outputStream);
            printStream.println(s);

            //byte[] bytes = new byte[2048];
            //outputStream.write(bytes, 0, jsonData.length());
            //outputStream.write(bytes);

            outputStream.close();
            printStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /****************************************************************
     * buffer流 测试
     * @author: wg
     * @time: 2020/6/19 16:25
     ****************************************************************/
    public static void jsonDataToFileByBufferedWritter(String path, String jsonData) {
        try {

            File file = ResourceUtils.getFile(path);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file);

            //fileWriter.write(jsonData);

            // 也可不必开启缓冲流 直接用文件流
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonData);

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
