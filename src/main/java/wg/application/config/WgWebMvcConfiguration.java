package wg.application.config;

import org.springframework.context.annotation.Configuration;
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
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
          .addResourceLocations("classpath:/static/")
          .addResourceLocations("classpath:/templates/");

        super.addResourceHandlers(registry);
    }

    //@Bean
    //public TicketFilter initTicketFilter(){
    //    return  new TicketFilter();
    //}



}
