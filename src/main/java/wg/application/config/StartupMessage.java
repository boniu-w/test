package wg.application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/9/21 15:13
 * @version
 * @Copyright
 *************************************/
@Component
@Slf4j
@Order(value = 3)
public class StartupMessage implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // System.out.println("启动完成");
        log.info("启动完成");
    }
}
