package wg.application;

// import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StreamUtils;
import wg.application.util.RedisUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
@MapperScan("wg.application.mapper")
// @EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
// @ComponentScan(basePackages = {"wg.application"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Aspect.class})})
@ComponentScan(basePackages = {"wg.application"})
// 用于定时任务
@EnableScheduling
// @NacosPropertySource(dataId = "test",groupId = "DEFAULT_GROUP",autoRefreshed = true)
@EnableCaching
public class TestApplication {

    // public static void main(String[] args) {
    //     ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, args);
    //     // context.addApplicationListener(new ListenerOf());
    //
    //     // ConfigurableEnvironment environment = context.getEnvironment();
    //     // Map<String, Object> systemProperties = environment.getSystemProperties();
    //     // systemProperties.forEach((k, v) -> System.out.println(k + ": " + v));
    // }
    
    public static void main(String[] args) throws IOException {
        // 加载 application-wg.yml 文件
        ClassPathResource resource = new ClassPathResource("application-wg.yml");
        String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        
        // 输出文件内容
        // System.out.println(content);
        
        // 读取 spring.redis.host 属性的值
        SpringApplication app = new SpringApplication(TestApplication.class);
        Environment env = app.run(args).getEnvironment();
        String host = env.getProperty("spring.redis.host");
        
        // 输出属性值
        System.out.println("Redis Host: " + host);
        
        RedisUtil.getAll();
        
        Set<String> keys = RedisUtil.getAllKeys("*");
        for (String key : keys) {
            Object value = RedisUtil.get(key);
            System.out.println(key + ": " + value);
        }
    }

}


