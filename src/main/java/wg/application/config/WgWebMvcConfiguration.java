package wg.application.config;

import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class WgWebMvcConfiguration extends WebMvcConfigurationSupport {

    /***************************************************
     * 这里配置静态资源文件的路径导包都是默认的直接导入就可以
     * @author: wg
     * @time: 2020/4/20 15:54
     ***************************************************/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
          .addResourceLocations("classpath:/static/")
          .addResourceLocations("classpath:/static/**")
          .addResourceLocations("classpath:/static/**/**")
          .addResourceLocations("classpath:/templates/")
          .addResourceLocations("classpath:/templates/**")
          .addResourceLocations("classpath:/static/bootstrap/337/**")
          .addResourceLocations("classpath:/public/**")
        ;
        //super.addResourceHandlers(registry);
    }

    //@Override
    //public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //    registry.addResourceHandler("/**")
    //      .addResourceLocations("file:" + upLoadPath + "//", "file:" + webAppPath + "//")
    //      .addResourceLocations(staticLocations.split(","));
    //}

    //@Bean
    //public TicketFilter initTicketFilter(){
    //    return  new TicketFilter();
    //}

    /************************************************************************
     * @author: wg
     * @description: 跨域 解决
     * @params:
     * @return:
     * @createTime: 16:33  2022/3/2
     * @updateTime: 16:33  2022/3/2
     ************************************************************************/
    @Bean
    @Conditional(CorsFilterCondition.class)
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        //是否允许请求带有验证信息
        corsConfiguration.setAllowCredentials(true);
        // 允许访问的客户端域名
        corsConfiguration.addAllowedOrigin("*");
        // 允许服务端访问的客户端请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许访问的方法名,GET POST等
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
