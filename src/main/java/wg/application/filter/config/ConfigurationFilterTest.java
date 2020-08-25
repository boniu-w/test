package wg.application.filter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*************************************************************
 * @Package wg.application.filter.config
 * @author wg
 * @date 2020/8/24 16:49
 * @version
 * @Copyright
 *************************************************************/
@Configuration
public class ConfigurationFilterTest {

    @Bean
    public WgFilter initFilter(){

        return  new WgFilter();
    }

}
