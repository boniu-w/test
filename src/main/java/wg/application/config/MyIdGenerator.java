package wg.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wg.application.algorithm.IdWorker1;
import wg.application.util.IpUtil;

/************************************************************************
 * @author: wg
 * @description: jpa中 id 用雪花算法生成
 * @params:
 * @return:
 * @createTime: 11:11  2022/8/26
 * @updateTime: 11:11  2022/8/26
 ************************************************************************/
public class MyIdGenerator {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 终端ID
     */
    public static long WORKER_ID;

    /**
     * 数据中心id
     */
    public static final long DATACENTER_ID = 1;

    public static IdWorker1 idWorker1;

    static {
        String interIP1 = null;

        try {
            interIP1 = IpUtil.getInterIP1();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        WORKER_ID = IpUtil.ipToLong(interIP1);
        idWorker1 = new IdWorker1(WORKER_ID % 32, DATACENTER_ID, 1);
    }

    public long generate() {
        return idWorker1.nextId();
    }

}
