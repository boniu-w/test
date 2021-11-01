package wg.application.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        logger.info("------------info--------------{}",1);
        logger.error("------------error--------------{}",2);
        logger.debug("------------debug--------------{}",3);
        logger.trace("------------trace--------------{}",4);
        logger.warn("------------warn--------------{}",5);
    }

}
