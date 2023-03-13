package wg.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wg.application.interceptor.FangshuaInterceptor;
import wg.application.interceptor.MyInterceptorTest;

import javax.annotation.Resource;

/************************************************************************
 * @author: wg
 * @description: 拦截器拦截的是controller
 * @params:
 * @return:
 * @createTime: 11:23  2023/3/13
 * @updateTime: 11:23  2023/3/13
 ************************************************************************/
@Configuration
@EnableWebMvc
public class MyWebConfig implements WebMvcConfigurer {

    @Autowired
    private FangshuaInterceptor interceptor;

    @Resource
    private MyInterceptorTest myInterceptorTest;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
        registry.addInterceptor(myInterceptorTest)
        ;

        // 这两行 不管用
        // .addPathPatterns("/wg/application/controller/")
        // .excludePathPatterns("/static/**"); // 表示排除静态资源的访问，避免拦截器对静态资源的干扰。
    }

    /************************************************************************
     * @author: wg
     * @description: 测试失败, 拦截器 在系统启动时起作用了, 并没有说拦截方法
     * @params:
     * @return:
     * @createTime: 11:17  2023/3/13
     * @updateTime: 11:17  2023/3/13
     ************************************************************************/
    // @Override
    // protected void addInterceptors(InterceptorRegistry registry) {
    //     System.out.println("拦截器测试");
    //     registry.addInterceptor(myInterceptorTest)
    //             .addPathPatterns("/wg/application/service/impl/**")
    //             .excludePathPatterns("/static/**"); // 表示排除静态资源的访问，避免拦截器对静态资源的干扰。
    // }
}