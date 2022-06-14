package wg.application.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import wg.application.entity.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:37 2022/3/30
 * @updateTime: 10:37 2022/3/30
 ************************************************************************/
public class JacksonTest {


    public void test1() {
        User user = new User();
        user.setName("123");
        user.setBirthday(LocalDateTime.now());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.findAndRegisterModules(); // 注册 jsr310
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.US));  // 不管用, 但也管一点用

            String userJsonString = objectMapper.writeValueAsString(user);
            System.out.println(userJsonString);

            JsonNode jsonNode = objectMapper.readTree(userJsonString);
            System.out.println("JacksonTest.test1");

            ObjectReader reader = objectMapper.reader();
            System.out.println("JacksonTest.test1");

            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            String value = objectMapper.writeValueAsString(Instant.now());
            System.out.println(value);

            String now = objectMapper.writeValueAsString(LocalDateTime.now());
            System.out.println(now);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
