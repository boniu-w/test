package wg.application;

// import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import wg.application.listener.ListenerOf;

@SpringBootApplication
@MapperScan("wg.application.dao")
// @EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"wg.application"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Aspect.class})})
// 用于定时任务
@EnableScheduling
// @NacosPropertySource(dataId = "test",groupId = "DEFAULT_GROUP",autoRefreshed = true)
public class TestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, args);
        // context.addApplicationListener(new ListenerOf());
    }

}


