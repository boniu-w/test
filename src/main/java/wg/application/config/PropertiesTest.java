package wg.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/********************************************************
 * @Package wg.application.config
 * @author wg
 * @date 2020/6/16 9:43
 * @version
 * @Copyright
 ********************************************************/
@Component
public class PropertiesTest {

    @Value("${wg.name}")
    String name;

    public String getName() {
        return name;
    }
}
