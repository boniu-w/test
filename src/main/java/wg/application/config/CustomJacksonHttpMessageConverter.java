package wg.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


/************************************************************************
 * @author: wg
 * @description: 解决 Long 型传到前端, 前端精度缺失问题
 * @createTime: 15:42  2022/1/18
 * @updateTime: 15:42  2022/1/18
 ************************************************************************/
@Configuration
public class CustomJacksonHttpMessageConverter {
    @Bean
    public HttpMessageConverters jacksonHttpMessageConverters() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);

        return new HttpMessageConverters(jackson2HttpMessageConverter);
    }
}
