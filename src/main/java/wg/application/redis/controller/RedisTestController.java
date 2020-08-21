package wg.application.redis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import wg.application.vo.Result;

/*************************************************************
 * @Package wg.application.redis.controller
 * @author wg
 * @date 2020/8/21 11:20
 * @version
 * @Copyright
 *************************************************************/
@RestController
@RequestMapping(value = "/redisTestController")
public class RedisTestController {



    @RequestMapping(value = "/redisTest1")
    public Result redisTest1(){
        Jedis jedis = new Jedis("localhost");

        String info = jedis.info();
        System.out.println(info);

        System.out.println("redis is running  "+jedis.ping());
        return Result.ok();
    }


}
