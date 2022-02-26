package wg.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
public class NacosApplication {

    @Resource
    ConfigurableApplicationContext configurableApplicationContext;

    @Resource
    Environment environment;

    // @Value("${app.server1.config}")
    // private String config1;

    @GetMapping(value = "/config1")
    public String getConfig1() {
        String property = configurableApplicationContext.getEnvironment().getProperty("app.server1.config");
        System.out.println("property : " + property);

        Properties properties = System.getProperties();
        properties.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println();

        ConfigurableEnvironment configurableEnvironment = configurableApplicationContext.getEnvironment();
        Map<String, Object> systemProperties = configurableEnvironment.getSystemProperties();
        systemProperties.forEach((k, v) -> System.out.println(k + ": " + v));

        String[] activeProfiles = environment.getActiveProfiles();


        return null;
    }

    public static void main(String[] args) {
        // // 是否开启鉴权
        //  String enabled = "false";
        //  // 是否单机模式启动
        //  String standalone = "true";
        //
        //  System.setProperty("nacos.standalone", standalone);
        //  System.setProperty("nacos.core.auth.enabled", enabled);
        //  System.setProperty("server.tomcat.basedir","logs");
        //  System.setProperty("server.port","8848");
        ConfigurableApplicationContext applicationContext = SpringApplication.run(NacosApplication.class, args);
        String property = applicationContext.getEnvironment().getProperty("app.server1.config");
        System.out.println("spring boot property : " + property);
    }

}
