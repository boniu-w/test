package wg.application;

import com.google.gson.stream.JsonReader;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import wg.application.entity.User;
import wg.application.jackson.CarbonSteelSandErosion;
import wg.application.jackson.QuantitativeRbiDetailDTO;
import wg.application.util.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/************************************************************************
 * author: wg
 * description: JsonTest 
 * createTime: 15:48 2024/5/15
 * updateTime: 15:48 2024/5/15
 ************************************************************************/
public class JsonTest {

    @Test
    public void test() {
        String jsonFilePath = "wg/application/json/test.json";
        ClassLoader classLoader = JsonReader.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(jsonFilePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + jsonFilePath);
            }
            String jsonContent = new Scanner(inputStream, StandardCharsets.UTF_8.name()).useDelimiter("\\A").next();
            System.out.println(jsonContent);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // 使用 ClassLoader 获取资源文件
            Resource resource = new ClassPathResource(jsonFilePath, classLoader);
            if (!resource.exists()) {
                throw new IllegalArgumentException("File not found: " + jsonFilePath);
            }

            // 读取 JSON 文件内容
            InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            String jsonContent = FileCopyUtils.copyToString(reader);

            // 打印 JSON 字符串
            System.out.println("JSON Content: " + jsonContent);
            QuantitativeRbiDetailDTO aClass = JsonUtil.toClass(jsonContent, QuantitativeRbiDetailDTO.class);

            System.out.println("aClass = " + aClass);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        User xiao1 = new User();
        xiao1.setName("xiao1");
        xiao1.setBirthday(LocalDateTime.now());

        User xiao = new User();
        xiao.setName("xiao");
        xiao.setBirthday(LocalDateTime.now());

        User zhong = new User();
        zhong.setName("zhong");
        zhong.setBirthday(LocalDateTime.now().plus(200, ChronoUnit.SECONDS));

        ArrayList<User> users = new ArrayList<>();
        users.add(xiao1);
        users.add(xiao);
        users.add(zhong);

        String jsonString = JsonUtil.toJsonString(users);
        System.out.println("jsonString = " + jsonString);

        List<User> list = JsonUtil.toObjList(jsonString, User.class);
        System.out.println("list = " + list);
    }
}
