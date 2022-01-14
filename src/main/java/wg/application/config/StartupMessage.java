package wg.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import wg.application.log.LogTest;

/**************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/9/21 15:13
 * @version
 * @Copyright
 *************************************/
@Component
@Order(value = 3)
public class StartupMessage implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartupMessage.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // System.out.println("启动完成");
        logger.info("启动完成");
    }
}
