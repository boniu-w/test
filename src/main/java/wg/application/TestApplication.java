package wg.application;

//import org.mybatis.spring.annotation.MapperScan;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

@SpringBootApplication
//@MapperScan("wg.application.mapper")
//"wg.application.beichengamble.mapper.GambleMapper",
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Aspect.class})})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
        System.out.println("--TestApplication启动完成--");
    }

}


