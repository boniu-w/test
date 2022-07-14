package wg.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wg.application.interceptor.FangshuaInterceptor;

public class MyWebConfig implements WebMvcConfigurer {
 
    @Autowired
    private FangshuaInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}