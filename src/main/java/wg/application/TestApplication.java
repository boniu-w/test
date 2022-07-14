package wg.application;

// import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

@SpringBootApplication
@MapperScan("wg.application.mapper")
// @EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
// @ComponentScan(basePackages = {"wg.application"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Aspect.class})})
@ComponentScan(basePackages = {"wg.application"})
// 用于定时任务
@EnableScheduling
// @NacosPropertySource(dataId = "test",groupId = "DEFAULT_GROUP",autoRefreshed = true)
public class TestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, args);
        // context.addApplicationListener(new ListenerOf());

        // ConfigurableEnvironment environment = context.getEnvironment();
        // Map<String, Object> systemProperties = environment.getSystemProperties();
        // systemProperties.forEach((k, v) -> System.out.println(k + ": " + v));
    }

}


