package wg.application;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("wg.application.dao")
// @EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"wg.application"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Aspect.class})})
// 用于定时任务
@EnableScheduling
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
        // System.out.println("--TestApplication启动完成--");
    }

    // SpringBoot2.x配置HTTPS,并实现HTTP访问自动转向HTTPS -> 不管用
    // @Bean
    // public ServletWebServerFactory servletContainer() {
    //     TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
    //         @Override
    //         protected void postProcessContext(Context context) {
    //             SecurityConstraint securityConstraint = new SecurityConstraint();
    //             securityConstraint.setUserConstraint("CONFIDENTIAL");
    //             SecurityCollection collection = new SecurityCollection();
    //             collection.addPattern("/*");
    //             securityConstraint.addCollection(collection);
    //             context.addConstraint(securityConstraint);
    //         }
    //     };
    //     tomcat.addAdditionalTomcatConnectors(httpConnector());
    //     return tomcat;
    // }

    // @Bean
    // public Connector httpConnector() {
    //     Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    //     connector.setScheme("http");
    //     connector.setPort(8000); // 监听Http的端口
    //     connector.setSecure(false);
    //     connector.setRedirectPort(33333); // 监听Http端口后转向Https端口, 33333 是自己设置的端口
    //     return connector;
    // }

}


