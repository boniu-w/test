package wg.application.controller;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/9/21 15:27
 * @version
 * @Copyright
 *************************************/
@Component
@Order(value = 1)
public class StartupMessage2 implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("startup finish");
    }
}
