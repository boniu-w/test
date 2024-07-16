package wg.application.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


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
                .addResourceLocations("classpath:/city/**")
                .addResourceLocations("classpath:/resources/static/**")
                .addResourceLocations("classpath:/resources/**")
                .addResourceLocations("classpath:/META-INF/resources/static/**")
                .addResourceLocations("classpath:/META-INF/resources/**")
        ;
    }

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

    /************************************************************************
     * @author: wg
     * @description: 当实体类的日期类型为 Date 时, 不用再写 下面的注解, 当 为 LocalDateTime 时, 还是需要写的
     *     ` @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd") `
     *     ` @DateTimeFormat(pattern = "yyyy-MM-dd") `
     *      这样的注解
     * @params:
     * @return:
     * @createTime: 12:29  2022/3/24
     * @updateTime: 12:29  2022/3/24
     ************************************************************************/
    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();

        //返回的日期格式转换
        // mapper.findAndRegisterModules(); // 注册 jsr310 , 不管用, 可能是版本问题, 待解决
        // mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // 不管用, 可能是版本问题, 待解决
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        //返回时Long类型转String类型
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(BigDecimal.class, new DecimalSerializer()); // 不管用
        
        mapper.registerModule(simpleModule);
        converter.setObjectMapper(mapper);
        return converter;
    }
}
